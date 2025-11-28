package com.example.bcsd.global.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CustomExceptionResponse> handleException(CustomException e) {
        ErrorCode errorCode = e.getErrorCode();
        CustomExceptionResponse response = CustomExceptionResponse.from(errorCode);
        return new ResponseEntity<>(response, errorCode.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomExceptionResponse> handleException(MethodArgumentNotValidException e) {
        List<String> messages = e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        CustomExceptionResponse response = new CustomExceptionResponse(
                "VALIDATION_ERROR",
                HttpStatus.BAD_REQUEST.value(),
                String.join(", ", messages)
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
