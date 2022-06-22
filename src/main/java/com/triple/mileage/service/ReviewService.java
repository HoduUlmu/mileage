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

import java.util.Collections;
import java.util.List;
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
    public void add(UUID reviewId, String content, List<UUID> attachedPhotoIds, UUID userId, UUID placeId) {
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

        List<Image> images = attachedPhotoIds.stream()
                .map((imageId) -> new Image(imageId, review))
                .collect(Collectors.toList());
        imageRepository.saveAll(images);

        if (addPoint > 0) user.changePoint(addPoint);
    }

    @Transactional
    public void mod(UUID reviewId, String content, List<UUID> attachedPhotoIds, UUID userId, UUID placeId) {
        Review review = getReview(reviewId);
        Place place = review.getPlace();
        User user = review.getUser();

        matchValidation(userId, placeId, user, place);
        Long changePoint = pointPolicy.calculate(content, attachedPhotoIds.size(), place, user);

        List<Image> oldImages = imageRepository.findAllByReview(review);
        List<Image> removeImages = oldImages.stream()
                .filter(image -> !attachedPhotoIds.contains(image.getId()))
                .collect(Collectors.toList());
        List<Image> newImages = attachedPhotoIds.stream()
                .filter(imageId -> oldImages.stream().noneMatch(image -> image.getId() == imageId))
                .map(imageId -> new Image(imageId, review))
                .collect(Collectors.toList());
        imageRepository.saveAll(newImages);
        imageRepository.deleteAllInBatch(removeImages);


        review.change(content,  changePoint);
        long newPoint = changePoint - review.getGivenPoint();
        if (newPoint != 0) user.changePoint(newPoint);
    }

    @Transactional
    public void delete(UUID reviewId, UUID userId, UUID placeId) {
        Review review = getReview(reviewId);
        User user = review.getUser();
        matchValidation(userId, placeId, user, review.getPlace());
        Long givenPoint = review.getGivenPoint();
        if (givenPoint != 0) user.changePoint(-givenPoint);
        imageRepository.deleteAllBy(reviewId);
        reviewRepository.deleteAllInBatch(Collections.singletonList(review));
    }

    private void matchValidation(UUID userId, UUID placeId, User user, Place place) {
        assert user.getId() != null && place.getId() != null;
        if (!user.getId().equals(userId)) throw new UserNotMatchException();
        if (!place.getId().equals(placeId)) throw new PlaceNotMatchException();
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
