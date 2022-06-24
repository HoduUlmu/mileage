package com.triple.mileage.controller;

import com.triple.mileage.annotation.Type;
import com.triple.mileage.controller.mapper.action.ReviewActionMapper;
import com.triple.mileage.dto.ReviewRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.triple.mileage.constant.TypeEnum.REVIEW;

@RestController
@RequiredArgsConstructor
public class EventController {

    private final ReviewActionMapper mapper;

    @Type(REVIEW)
    @PostMapping("/events")
    public void reviewEvents(@RequestBody @Valid ReviewRequestDto requestDto) {
        mapper.action(requestDto.getAction(), requestDto);
    }
}

