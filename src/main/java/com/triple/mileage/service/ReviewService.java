package com.triple.mileage.service;

import com.triple.mileage.domain.Image;
import com.triple.mileage.domain.Place;
import com.triple.mileage.domain.Review;
import com.triple.mileage.domain.User;
import com.triple.mileage.exception.custom.business.*;
import com.triple.mileage.repository.ImageRepository;
import com.triple.mileage.repository.PlaceRepository;
import com.triple.mileage.repository.ReviewRepository;
import com.triple.mileage.repository.UserRepository;
import com.triple.mileage.service.policy.TextImageFirstReviewPointPolicy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;
    private final ImageRepository imageRepository;
    private final ReviewRepository reviewRepository;
    private final TextImageFirstReviewPointPolicy pointPolicy;

    @Transactional
    public void add(UUID reviewId, String content, Set<UUID> attachedPhotoIds, UUID userId, UUID placeId) {
        User user = getUser(userId);
        Place place = getPlace(placeId);

        Long addPoint = pointPolicy.calculate(content, attachedPhotoIds.size(), place, user);

        Review review = Review.builder()
                .reviewId(reviewId)
                .content(content)
                .givenPoint(addPoint)
                .user(user)
                .place(place)
                .build();
        reviewRepository.save(review);

        Set<Image> images = attachedPhotoIds.stream()
                .map((imageId) -> new Image(imageId, review))
                .collect(Collectors.toSet());
        imageRepository.saveAll(images);

        if (addPoint > 0) user.changePoint(addPoint);
    }

    @Transactional
    public void mod(UUID reviewId, String content, Set<UUID> attachedPhotoIds, UUID userId, UUID placeId) {
        Review review = getReview(reviewId);
        Place place = getPlace(placeId);
        User user = getUser(userId);

        if (!review.getUser().equals(user)) throw new UserNotMatchException();
        if (!review.getPlace().equals(place)) throw new PlaceNotMatchException();

        Long changePoint = pointPolicy.calculate(content, attachedPhotoIds.size(), place, user);
        review.change(content, attachedPhotoIds, changePoint);
        long newPoint = changePoint - review.getGivenPoint();
        if (newPoint != 0) user.changePoint(newPoint);
    }

    @Transactional
    public void delete(UUID reviewId, UUID userId, UUID placeId) {
        Review review = getReview(reviewId);
        User user = getUser(userId);

        if (!review.getUser().equals(user)) throw new UserNotMatchException();
        if (!review.getPlace().getPlaceId().equals(placeId)) throw new PlaceNotMatchException();
        user.changePoint(-review.getGivenPoint());
        reviewRepository.delete(review);
    }

    private Review getReview(UUID reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(ReviewNotFoundException::new);
    }

    private Place getPlace(UUID placeId) {
        return placeRepository.findById(placeId).orElseThrow(PlaceNotFoundException::new);
    }

    private User getUser(UUID userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }
}
