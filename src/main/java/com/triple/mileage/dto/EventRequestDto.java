package com.triple.mileage.dto;

import com.triple.mileage.web.constant.ActionEnum;
import com.triple.mileage.web.constant.TypeEnum;
import lombok.Getter;

@Getter
public class EventRequestDto {
    private TypeEnum type;
    private ActionEnum action;
}
