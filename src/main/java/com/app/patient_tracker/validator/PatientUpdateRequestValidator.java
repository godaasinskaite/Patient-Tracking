package com.app.patient_tracker.validator;

import com.app.patient_tracker.dto.PatientUpdateRequest;
import com.app.patient_tracker.exception.ApplicationException;
import com.app.patient_tracker.exception.ErrorCode;
import com.app.patient_tracker.model.Patient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDate;

@Service
@Slf4j
public class PatientUpdateRequestValidator {

    public void validateGivenDataForUpdate(final PatientUpdateRequest patientUpdateRequest, final Patient patient) throws ApplicationException, IllegalAccessException {
        log.info("Validating given data for update.");
        final Field[] fieldsToValidate = patientUpdateRequest.getClass().getDeclaredFields();
        for (Field field : fieldsToValidate) {
                Object updatedValue = field.get(patientUpdateRequest);
                Object originalValue = field.get(patient);
                if (!isFieldValid(updatedValue, originalValue)) {
                    log.error(field.getName() + " was empty or the same as the old one.");
                    throw new ApplicationException(field.getName() + " is not correct or equals to the old one.", ErrorCode.INVALID_DATA_EXCEPTION);
                }
        }
    }

    private boolean isFieldValid(Object updatedValue, Object originalValue) {
        if (updatedValue == null) {
            return false;
        }
        if (updatedValue instanceof String) {
            return !((String) updatedValue).isEmpty() && !updatedValue.equals(originalValue);
        } else if (updatedValue instanceof LocalDate) {
            return !updatedValue.equals(originalValue);
        }
        return false;
    }
}
