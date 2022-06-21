package com.triple.mileage.exception;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.triple.mileage.exception.custom.ErrorCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

    @JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    private final LocalDateTime time = LocalDateTime.now();
    private String code;
    private String message;

    protected ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ErrorResponse of(ErrorCode code) {
        return new ErrorResponse(code.name(), code.getMessage());
    }
}
