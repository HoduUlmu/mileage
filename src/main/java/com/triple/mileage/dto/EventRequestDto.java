package com.triple.mileage.dto;

import com.triple.mileage.web.constant.ActionEnum;
import com.triple.mileage.web.constant.TypeEnum;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class EventRequestDto {

    @NotNull(message = "대상이 필요합니다")
    private TypeEnum type;

    @NotNull(message = "액션이 필요합니다")
    private ActionEnum action;
}
