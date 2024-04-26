package com.app.patient_tracker.controller;


import com.app.patient_tracker.dto.PatientRequestDto;
import com.app.patient_tracker.dto.PatientResponseDto;
import com.app.patient_tracker.dto.PatientUpdateRequest;
import com.app.patient_tracker.exception.*;
import com.app.patient_tracker.model.Patient;
import com.app.patient_tracker.service.PatientService;
import com.app.patient_tracker.validator.PatientRequestValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/patients")
@Slf4j
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;
    private final PatientRequestValidator patientRequestValidator;

    @GetMapping("/all")
    public ResponseEntity<?> findAllPatients() throws PatientNotFoundException {
        List<Patient> patients = patientService.getAllPatients();
        return ResponseEntity.status(HttpStatus.OK).body(patients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findPatientById(@PathVariable final Long id) throws PatientNotFoundException {
        Patient patient = patientService.getPatientById(id);
        return ResponseEntity.status(HttpStatus.OK).body(patient);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addNewPatient(@RequestBody final PatientRequestDto patientRequestDto) throws MandatoryFieldsMissingException {
        patientRequestValidator.validatePatientRequest(patientRequestDto);
        List<PatientResponseDto> patients = patientService.addNewPatient(patientRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(patients);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updatePatient(@PathVariable final Long id, @RequestBody final PatientUpdateRequest patientUpdateRequest) throws PatientNotFoundException, InvalidDataException, PatientUpdateException {
        patientService.updatePatientInfo(id, patientUpdateRequest);
        return ResponseEntity.status(HttpStatus.OK).body("Patient updated successfully.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePatientById(@PathVariable final Long id) throws DeleteOperationException {
        patientService.deletePatientById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Patient successfully deleted.");
    }
}
