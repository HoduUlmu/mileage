package com.triple.mileage.exception.custom.business;

import static com.triple.mileage.exception.custom.ErrorCode.USER_NOT_MATCH;

public class UserNotMatchException extends BusinessException {

    public UserNotMatchException() {
        super(USER_NOT_MATCH);
    }
}
