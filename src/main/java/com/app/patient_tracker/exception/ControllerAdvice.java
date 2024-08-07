package com.app.patient_tracker.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class ControllerAdvice {
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResponse> handleApplicationException(ApplicationException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(e.getMessage())
                .errorCode(e.getErrorCode())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(errorResponse);
    }
}
