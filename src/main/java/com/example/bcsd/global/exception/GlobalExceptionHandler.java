package com.example.bcsd.global.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CustomExceptionResponse> handleException(
            CustomException ex,
            HttpServletRequest request
    ) {
        request.setAttribute("exceptionMessage", ex.getMessage());

        CustomExceptionResponse response = CustomExceptionResponse.from(ex.getErrorCode());

        return ResponseEntity
                .status(response.status())
                .body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomExceptionResponse> handleException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        request.setAttribute("exceptionMessage", ex.getMessage());

        ErrorCode code = ErrorCode.NULL_VALUE_NOT_ALLOWED;

        return ResponseEntity
                .status(code.getStatus())
                .body(CustomExceptionResponse.from(code));
    }

}
