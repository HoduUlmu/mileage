package com.triple.mileage.controller;

import com.triple.mileage.controller.mapper.RequestDtoConverter;
import com.triple.mileage.controller.mapper.ReviewActionMapper;
import com.triple.mileage.dto.EventRequestDto;
import com.triple.mileage.dto.ReviewRequestDto;
import com.triple.mileage.web.constant.TypeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class EventController {

    private final RequestDtoConverter converter;
    private final ReviewActionMapper mapper;

    @PostMapping("/events")
    public void events(HttpServletRequest request, @RequestBody @Valid EventRequestDto eventRequestDto, BindingResult bindingResult) {
        if (eventRequestDto.getType() == TypeEnum.REVIEW) {
            ReviewRequestDto requestDto = converter.toReviewDto(request);
            mapper.action(eventRequestDto.getAction(), requestDto);
        }
    }
}

