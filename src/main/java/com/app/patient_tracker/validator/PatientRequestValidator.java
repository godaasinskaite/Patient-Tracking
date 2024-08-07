package com.app.patient_tracker.validator;

import com.app.patient_tracker.dto.PatientRequestDto;
import com.app.patient_tracker.exception.ApplicationException;
import com.app.patient_tracker.exception.ErrorCode;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Service
@Slf4j
public class PatientRequestValidator {

    public void validatePatientRequest(final PatientRequestDto patientRequestDto) throws ApplicationException {
        if (patientRequestDto == null) {
            log.error("Patient request was empty/incorrect.");
            throw new ApplicationException("Wrong request.", ErrorCode.PATIENT_REQUEST_DTO_EXCEPTION);
        }
        checkMandatoryField(patientRequestDto.getName(), "FirstName");
        checkMandatoryField(patientRequestDto.getLastName(), "LastName");
        checkMandatoryFieldDob(patientRequestDto.getDob());
    }

    private void checkMandatoryField(Object field, String fieldName) throws ApplicationException {
        if (field == null) {
            log.error("Mandatory field " + fieldName + " is missing.");
            throw new ApplicationException("Mandatory field " + fieldName + " is missing", ErrorCode.MANDATORY_FIELD_MISSING_EXCEPTION);
        }
    }

    private void checkMandatoryFieldDob(LocalDate dob) throws ApplicationException {
        if (dob == null) {
            log.error("Mandatory field Date of Birth is missing.");
            throw new ApplicationException("Mandatory field Date of Birth is missing.", ErrorCode.MANDATORY_FIELD_MISSING_EXCEPTION);
        } else if (dob.isAfter(LocalDate.now())) {
            log.error("Incorrect date of birth.");
            throw new ApplicationException("Date of birth is incorrect.", ErrorCode.PATIENT_REQUEST_DTO_EXCEPTION);
        }
    }
}
