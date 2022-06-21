package com.triple.mileage.service.policy;

import com.triple.mileage.domain.Place;
import com.triple.mileage.domain.Review;
import com.triple.mileage.domain.User;
import com.triple.mileage.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TextImageFirstReviewPointPolicy {

    private final ReviewRepository reviewRepository;

    public Long calculate(String text, int imageNum, Place place, User user) {
        long point = 0L;
        if (StringUtils.hasText(text)) point++;
        if (imageNum > 0) point++;

        Optional<Review> review = reviewRepository.findDistinctFirstByPlaceOrderByCreatedAt(place);
        if (review.isEmpty()) point++;
        else if (review.get().getUser() == user) point++;

        return point;
    }
}
