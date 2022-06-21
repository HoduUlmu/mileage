package com.triple.mileage.controller;

import com.triple.mileage.dto.EventRequestDto;
import com.triple.mileage.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.triple.mileage.web.constant.ActionEnum.ADD;
import static com.triple.mileage.web.constant.TypeEnum.REVIEW;

@RestController
@RequiredArgsConstructor
public class EventController {

    private final ReviewService reviewService;

    @PostMapping("/events")
    public void events(@RequestBody EventRequestDto requestDto) {
        if (requestDto.getType() == REVIEW) {
            if (requestDto.getAction() == ADD)
                reviewService.add(
                        requestDto.getReviewId(),
                        requestDto.getContent(),
                        requestDto.getAttachedPhotoIds(),
                        requestDto.getUserId(),
                        requestDto.getPlaceId());
        }
    }
}

