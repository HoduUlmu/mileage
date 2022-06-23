package com.triple.mileage.controller;

import com.triple.mileage.web.constant.ActionEnum;

public interface ActionMapper<T> {
    void action(ActionEnum action, T requestDto);
}
