package com.triple.mileage.controller.mapper;

import com.triple.mileage.dto.ReviewRequestDto;
import com.triple.mileage.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewServiceMapper implements CUDEventMapper<ReviewRequestDto> {

    private final ReviewService reviewService;

    @Override
    public void add(ReviewRequestDto requestDto) {
        requestDto.nullCheck();
        reviewService.add(
                requestDto.getReviewId(),
                requestDto.getContent(),
                requestDto.getAttachedPhotoIds(),
                requestDto.getUserId(),
                requestDto.getPlaceId());
    }

    @Override
    public void mod(ReviewRequestDto requestDto) {
        requestDto.nullCheck();
        reviewService.mod(
                requestDto.getReviewId(),
                requestDto.getContent(),
                requestDto.getAttachedPhotoIds(),
                requestDto.getUserId(),
                requestDto.getPlaceId()
        );
    }

    @Override
    public void delete(ReviewRequestDto requestDto) {
        reviewService.delete(
                requestDto.getReviewId(),
                requestDto.getUserId(),
                requestDto.getPlaceId()
        );
    }
}
