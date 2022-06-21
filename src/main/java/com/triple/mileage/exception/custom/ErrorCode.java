package com.triple.mileage.exception.custom;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Getter
public enum ErrorCode {

    // 400
    PLACE_NOT_FOUND(BAD_REQUEST, "요청한 이미지를 찾을 수 없습니다"),
    USER_NOT_FOUND(BAD_REQUEST, "요청한 사용자가 존재하지 않습니다"),
    REVIEW_NOT_FOUND(BAD_REQUEST, "요청한 리뷰가 존재하지 않습니다"),
    USER_NOT_MATCH(BAD_REQUEST, "요청한 사용자와 작성자가 일치하지 않습니다"),
    PLACE_NOT_MATCH(BAD_REQUEST, "요청한 장소와 작성 장소가 일치하지 않습니다"),
    INVALID_INPUT(BAD_REQUEST, "잘못된 입력값입니다"),

    INTERNAL_ERROR(INTERNAL_SERVER_ERROR, "요청 작업 처리 중 서버 에러가 발생했습니다"),
    ;

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
