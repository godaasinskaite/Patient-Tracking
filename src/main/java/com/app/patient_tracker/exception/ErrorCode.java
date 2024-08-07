package com.app.patient_tracker.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    PATIENT_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND),
    ASSESSMENT_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND),
    ASSESSMENT_UPDATE_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR),
    ATTENDANCE_MAPPING_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR),
    ATTENDANCE_NOT_FOUND(HttpStatus.NOT_FOUND),
    DELETE_OPERATION_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_DATA_EXCEPTION(HttpStatus.BAD_REQUEST),
    PATIENT_REQUEST_DTO_EXCEPTION(HttpStatus.BAD_REQUEST),
    MANDATORY_FIELD_MISSING_EXCEPTION(HttpStatus.BAD_REQUEST),
    PATIENT_UPDATE_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR),
    ASSESSMENT_REQUEST_EXCEPTION(HttpStatus.BAD_REQUEST),
    ATTENDANCE_REQUEST_EXCEPTION(HttpStatus.BAD_REQUEST);

    private final HttpStatus httpStatus;

    ErrorCode(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
