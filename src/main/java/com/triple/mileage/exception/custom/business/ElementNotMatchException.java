package com.triple.mileage.exception.custom.business;

import static com.triple.mileage.exception.custom.ErrorCode.ELEMENT_NOT_MATCH;

public class ElementNotMatchException extends BusinessException {
    public ElementNotMatchException() {
        super(ELEMENT_NOT_MATCH);
    }
}
