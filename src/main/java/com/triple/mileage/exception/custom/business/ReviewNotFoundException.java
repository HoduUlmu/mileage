package com.triple.mileage.exception.custom.business;


import static com.triple.mileage.exception.custom.ErrorCode.REVIEW_NOT_FOUND;

public class ReviewNotFoundException extends BusinessException {

    public ReviewNotFoundException() {
        super(REVIEW_NOT_FOUND);
    }
}
