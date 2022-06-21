package com.triple.mileage.exception.custom.business;

import static com.triple.mileage.exception.custom.ErrorCode.PLACE_NOT_MATCH;

public class PlaceNotMatchException extends BusinessException {

    public PlaceNotMatchException() {
        super(PLACE_NOT_MATCH);
    }
}
