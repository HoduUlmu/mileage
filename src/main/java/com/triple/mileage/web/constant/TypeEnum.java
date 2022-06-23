package com.triple.mileage.web.constant;

import com.triple.mileage.dto.ReviewRequestDto;
import lombok.Getter;

@Getter
public enum TypeEnum {
    REVIEW(ReviewRequestDto.class);

    private final Class<?> requestDtoClass;

    TypeEnum(Class<?> clazz) {
        this.requestDtoClass = clazz;
    }
}
