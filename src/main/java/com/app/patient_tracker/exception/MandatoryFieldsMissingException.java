package com.app.patient_tracker.exception;

public class MandatoryFieldsMissingException extends Exception {

    public MandatoryFieldsMissingException(String message) {
        super(message);
    }
}
