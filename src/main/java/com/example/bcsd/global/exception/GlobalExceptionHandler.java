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

        List<String> errorMessages = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        CustomExceptionResponse response = new CustomExceptionResponse(
                400,
                String.join(", ", errorMessages)
        );

        return ResponseEntity
                .badRequest()
                .body(response);
    }
}
