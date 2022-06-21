package com.triple.mileage.exception.custom.business;


import static com.triple.mileage.exception.custom.ErrorCode.PLACE_NOT_FOUND;

public class PlaceNotFoundException extends BusinessException {

    public PlaceNotFoundException() {
        super(PLACE_NOT_FOUND);
    }
}
