package com.example.bcsd.global.exception;

import java.util.List;

public record ValidationErrorResponse(
        int status,
        String message,
        List<FieldError> errors
) {
    public record FieldError(
            String field,
            String message,
            Object rejectedValue
    ) {
    }

    public static ValidationErrorResponse of(int status, String message, List<FieldError> errors) {
        return new ValidationErrorResponse(status, message, errors);
    }
}
