package com.triple.mileage.service;

import com.triple.mileage.domain.Place;
import com.triple.mileage.domain.Review;
import com.triple.mileage.domain.User;
import com.triple.mileage.repository.PlaceRepository;
import com.triple.mileage.repository.ReviewRepository;
import com.triple.mileage.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public void add(UUID reviewId, String content, List<UUID> attachedPhotoIds, UUID userId, UUID placeId) {

        User user = userRepository.findById(userId).orElseThrow();
        Place place = placeRepository.findById(placeId).orElseThrow();

        long reviewPoint = 0L;
        if (StringUtils.hasText(content)) reviewPoint++;
        if (!CollectionUtils.isEmpty(attachedPhotoIds)) reviewPoint++;
        if (!reviewRepository.existsByPlace(place)) reviewPoint++;
        if (reviewPoint > 0) user.addReviewPoint(reviewPoint);

        Review review = Review.builder()
                .reviewId(reviewId)
                .content(content)
                .user(user)
                .place(place)
                .build();
        reviewRepository.save(review);
    }
}
