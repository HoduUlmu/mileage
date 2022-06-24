package com.triple.mileage.controller.mapper.action;

import com.triple.mileage.constant.ActionEnum;

public interface ActionMapper<T> {
    void action(ActionEnum action, T requestDto);
}
