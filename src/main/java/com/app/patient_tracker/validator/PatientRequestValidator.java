package com.app.patient_tracker.validator;

import com.app.patient_tracker.dto.PatientRequestDto;
import com.app.patient_tracker.exception.MandatoryFieldsMissingException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class PatientRequestValidator {

    public void validatePatientRequest(final PatientRequestDto patientRequestDto) throws MandatoryFieldsMissingException {
        if (patientRequestDto == null || patientRequestDto.getName() == null || patientRequestDto.getLastName() == null || patientRequestDto.getDob() == null) {
            log.error("Patient request was empty or mandatory field(s) was empty.");
            throw new MandatoryFieldsMissingException("Mandatory fields missing.");
        }
    }
}
