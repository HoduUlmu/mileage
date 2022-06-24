package com.triple.mileage.service.policy;

import com.triple.mileage.domain.Place;
import com.triple.mileage.domain.Review;
import com.triple.mileage.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class TextImageFirstReviewPointPolicy {

    private final ReviewRepository reviewRepository;

    @Transactional(readOnly = true)
    public Long calculate(String text, int imageNum, boolean isFirst) {
        long point = 0L;
        if (StringUtils.hasText(text)) point++;
        if (imageNum > 0) point++;
        if (isFirst) point++;
        return point;
    }

    public boolean isFirstCheckMod(Review review) {
        return review.isFirst();
    }

    public boolean isFirstCheckAdd(Place place) {
        return reviewRepository.findDistinctFirstByPlaceOrderByCreatedAt(place).isEmpty();
    }
}
