package com.triple.mileage.exception;

import lombok.Getter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.stream.Collectors;

import static com.triple.mileage.exception.custom.ErrorCode.INVALID_INPUT;


@Getter
public class BindingErrorResponse extends ErrorResponse {

    private final List<FieldErrorDto> errors;

    private BindingErrorResponse(List<FieldErrorDto> errors) {
        super(INVALID_INPUT.name(), INVALID_INPUT.getMessage());
        this.errors = errors;
    }


    public static BindingErrorResponse of(BindingResult bindingResult) {
        return new BindingErrorResponse(FieldErrorDto.of(bindingResult));
    }


    @Getter
    private static class FieldErrorDto {
        private final String field;
        private final String rejectedValue;
        private final String message;

        private FieldErrorDto(String field, String rejectedValue, String message) {
            this.field = field;
            this.rejectedValue = rejectedValue;
            this.message = message;
        }

        private static List<FieldErrorDto> of(BindingResult bindingResult) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            return fieldErrors.stream().map(e -> new FieldErrorDto(
                    e.getField(),
                    e.getRejectedValue() == null ? "" : e.getRejectedValue().toString(),
                    e.getDefaultMessage()
            )).collect(Collectors.toList());
        }
    }
}
