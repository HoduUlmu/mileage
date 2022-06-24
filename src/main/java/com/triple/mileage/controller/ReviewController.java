package com.triple.mileage.controller;

import com.triple.mileage.controller.mapper.CUDControllerServiceMapper;
import com.triple.mileage.dto.ReviewRequestDto;
import com.triple.mileage.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

// review 컨트롤러의 역할을 수행
// 원래는 사용자 요청을 바로 받아 수행해야 하지만 앞단에 event 컨트롤러가 있어 mapper가 호출
@Component
@RequiredArgsConstructor
public class ReviewController implements CUDControllerServiceMapper<ReviewRequestDto> {

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
