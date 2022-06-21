package com.triple.mileage.exception.custom.business;


import static com.triple.mileage.exception.custom.ErrorCode.USER_NOT_FOUND;

public class UserNotFoundException extends BusinessException {

    public UserNotFoundException() {
        super(USER_NOT_FOUND);
    }
}
