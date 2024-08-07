package com.app.patient_tracker.exception;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorResponse {

    private String message;
    private ErrorCode errorCode;
    private LocalDateTime timestamp;
}
