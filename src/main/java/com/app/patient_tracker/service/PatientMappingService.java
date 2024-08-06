package com.app.patient_tracker.service;

import com.app.patient_tracker.dto.PatientRequestDto;
import com.app.patient_tracker.dto.PatientResponseDto;
import com.app.patient_tracker.model.Patient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatientMappingService {
    /**
     * Method to map api patient request to an entity suitable for database storage.
     * Creates new Patient entity using the data from DTO request.
     *
     * @param patientDto represents request class containing data.
     * @return The mapped Patient entity.
     */

    public Patient mapPatientToEntity(final PatientRequestDto patientDto) {
        return Patient.builder()
                .name(patientDto.getName())
                .lastName(patientDto.getLastName())
                .dob(patientDto.getDob())
                .contactInfo(patientDto.getContactInfo())
                .assessments(patientDto.getAssessments())
                .attendances(patientDto.getAttendances())
                .patientProgress(patientDto.getPatientProgress())
                .nextAppointment(patientDto.getNextAppointment())
                .build();
    }

    /**
     * Maps a list of Patient entities to a list of PatientResponseDto objects for api response.
     *
     * @param patients The list of Patient entities to be mapped to response DTOs.
     * @return A list of PatientResponseDto objects containing mapped patient information.
     */
    public List<PatientResponseDto> mapPatientsToResponse(final List<Patient> patients) {
        final List<PatientResponseDto> mappedPatients = new ArrayList<>();

        for (Patient patient : patients) {
            PatientResponseDto dto = PatientResponseDto.builder()
                    .id(patient.getId())
                    .name(patient.getName())
                    .lastName(patient.getLastName())
                    .dob(patient.getDob())
                    .contactInfo(patient.getContactInfo())
                    .assessments(patient.getAssessments())
                    .attendances(patient.getAttendances())
                    .patientProgress(patient.getPatientProgress())
                    .nextAppointment(patient.getNextAppointment())
                    .build();

            mappedPatients.add(dto);
        }
        return mappedPatients;
    }
}
