package com.triple.mileage.exception.custom.business;

import static com.triple.mileage.exception.custom.ErrorCode.INTERNAL_ERROR;

public class ConvertException extends BusinessException {
    public ConvertException(Throwable cause) {
        super(INTERNAL_ERROR, cause);
    }
}
