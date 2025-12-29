package com.example.bcsd.global.exception;

import jakarta.servlet.http.HttpServletRequest;
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
    public ResponseEntity<ValidationErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        request.setAttribute("exceptionMessage", ex.getMessage());

        List<ValidationErrorResponse.FieldError> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new ValidationErrorResponse.FieldError(
                        error.getField(),
                        error.getDefaultMessage(),
                        error.getRejectedValue()
                ))
                .toList();

        ValidationErrorResponse response = ValidationErrorResponse.of(
                400,
                "입력값 유효성 검증에 실패했습니다.",
                fieldErrors
        );

        return ResponseEntity
                .badRequest()
                .body(response);
    }
}
