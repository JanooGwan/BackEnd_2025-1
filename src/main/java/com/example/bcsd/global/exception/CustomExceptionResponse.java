package com.example.bcsd.global.exception;

public record CustomExceptionResponse(
        int status,
        String message
) {
    public static CustomExceptionResponse from(ErrorCode errorCode) {
        return new CustomExceptionResponse(
                errorCode.getStatus(),
                errorCode.getMessage()
        );
    }
}
