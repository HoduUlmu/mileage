package com.triple.mileage.service.policy;

import com.triple.mileage.domain.Place;
import com.triple.mileage.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class TextImageFirstReviewPointPolicy {

    private final ReviewRepository reviewRepository;

    public Long calculate(String text, int imageNum, Place place) {
        long point = 0L;
        if (StringUtils.hasText(text)) point++;
        if (imageNum > 0) point++;
        boolean isReviewExist = reviewRepository.existsByPlace(place);
        if (!isReviewExist) point++;
        else {

        }
        return point;
    }
}
