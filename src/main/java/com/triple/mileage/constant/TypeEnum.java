package com.triple.mileage.constant;

import lombok.Getter;

@Getter
public enum TypeEnum {
    REVIEW(1);

    private final int order;

    TypeEnum(int order) {
        this.order = order;
    }
}
