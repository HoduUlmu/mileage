package com.triple.mileage.exception.custom.business;

import com.triple.mileage.exception.custom.ErrorCode;

public class InternalServerException extends BusinessException {
    public InternalServerException(Throwable cause) {
        super(ErrorCode.INTERNAL_ERROR, cause);
    }
}
