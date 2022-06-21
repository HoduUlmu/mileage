package com.triple.mileage.controller;

import com.triple.mileage.dto.EventRequestDto;
import com.triple.mileage.service.ReviewService;
import com.triple.mileage.web.constant.ActionEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.triple.mileage.web.constant.ActionEnum.*;
import static com.triple.mileage.web.constant.TypeEnum.REVIEW;

@RestController
@RequiredArgsConstructor
public class EventController {

    private final ReviewService reviewService;

    @PostMapping("/events")
    public void events(@RequestBody EventRequestDto requestDto) {
        if (requestDto.getType() == REVIEW) {
            ActionEnum action = requestDto.getAction();
            if (action == ADD) {
                requestDto.nullCheck();
                reviewService.add(
                        requestDto.getReviewId(),
                        requestDto.getContent(),
                        requestDto.getAttachedPhotoIds(),
                        requestDto.getUserId(),
                        requestDto.getPlaceId());
            }
            else if (action == MOD)
                reviewService.mod(
                        requestDto.getReviewId(),
                        requestDto.getContent(),
                        requestDto.getAttachedPhotoIds(),
                        requestDto.getUserId(),
                        requestDto.getPlaceId()
                );
        }
    }
}

