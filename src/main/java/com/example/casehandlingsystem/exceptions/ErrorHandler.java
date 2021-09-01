package com.example.casehandlingsystem.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> onApiException(ApiError e) {
        return ResponseEntity.status(e.getCode())
                .body(new ErrorResponse(e.getCode(), e.getMessage()));
    }
}
