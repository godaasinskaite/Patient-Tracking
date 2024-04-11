package com.app.patient_tracker.validator;

import com.app.patient_tracker.dto.PatientUpdateRequest;
import com.app.patient_tracker.exception.InvalidDataException;
import com.app.patient_tracker.model.Patient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;

@Service
@Slf4j
public class PatientUpdateRequestValidator {

    public Boolean validateGivenDataForUpdate(PatientUpdateRequest patientUpdateRequest, Patient patient) throws InvalidDataException {

        String name = patientUpdateRequest.getName();
        String lastName = patientUpdateRequest.getLastName();
        LocalDate dob = patientUpdateRequest.getDob();
        String contactInfo = patientUpdateRequest.getContactInfo();

        if (name == null || Objects.equals(patient.getName(), name)) {
            log.error("Name was empty or the same as the old one.");
            throw new InvalidDataException("Name is not correct or equals to old one.");
        }

        if (lastName == null || Objects.equals(patient.getLastName(), lastName)) {
            log.error("LastName was empty or the same as the old one.");
            throw new InvalidDataException("LastName is not correct or equals to old one.");
        }

        if (dob == null || Objects.equals(patient.getDob(), dob)) {
            log.error("Provided DOB was incorrect or the same as the old one.");
            throw new InvalidDataException("Date of birth is not correct or equals to old one.");
        }

        if (contactInfo == null || Objects.equals(patient.getContactInfo(), contactInfo)) {
            log.error("ContactInfo was empty or the same as the old one.");
            throw new InvalidDataException("ContactInfo is not correct or equals to old one.");
        }
        return true;
    }
}
