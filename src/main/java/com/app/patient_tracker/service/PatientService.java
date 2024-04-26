package com.app.patient_tracker.service;

import com.app.patient_tracker.dto.*;
import com.app.patient_tracker.exception.*;
import com.app.patient_tracker.model.Attendance;
import com.app.patient_tracker.model.Patient;
import com.app.patient_tracker.repository.PatientRepository;
import com.app.patient_tracker.validator.PatientUpdateRequestValidator;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Service class responsible for managing patient related data operations.
 */
@Service
@Slf4j
@Data
public class PatientService {

    private final PatientRepository patientRepository;
    private final CacheManager cacheManager;
    private final Cache cache;
    private final PatientMappingService mappingService;
    private final PatientUpdateRequestValidator patientUpdateDataValidator;

    public PatientService(PatientRepository patientRepository, CacheManager cacheManager, PatientMappingService mappingService, PatientUpdateRequestValidator patientUpdateDataValidator) {
        this.patientRepository = patientRepository;
        this.cacheManager = cacheManager;
        this.cache = cacheManager.getCache("cache");
        this.mappingService = mappingService;
        this.patientUpdateDataValidator = patientUpdateDataValidator;
    }

    /**
     * Method checks for the next appointment for the given patient.
     * Updates the patient's next appointment field based on the first unattended attendance that occurs after current date.
     *
     * @param patient The patient for whom to check the next appointment.
     *                <p>
     *                Updates the next appointment date for the patient or sets null if no suitable appointment is found.
     */
    public void checkForNextAppointment(final Patient patient) {
        LocalDate localDate = patient.getAttendances().stream()
                .filter(attendance -> !attendance.getDidAttend() && attendance.getDateOfAttendance().isAfter(LocalDate.now()))
                .map(Attendance::getDateOfAttendance)
                .findFirst()
                .orElse(null);

        patient.setNextAppointment(localDate);
        patientRepository.save(patient);
        log.info("Patient nextAppointment updated");
    }

    /**
     * Method retrieves a list of patients from database.
     * Throws an exception if the patient list is empty.
     *
     * @return A list containing all patients stored in the database.
     * @throws PatientNotFoundException If the list is empty.
     */
    public List<Patient> getAllPatients() throws PatientNotFoundException {
        log.info("Looking for patients in DB.");
        List<Patient> patients = patientRepository.findAll();

        if (patients.isEmpty()) {
            log.error("DB is empty. No patients were found.");
            throw new PatientNotFoundException("No patients were found.");
        }

        log.info(patients.size() + " patients were found in the DB.");
        return patients;
    }

    /**
     * Method retrieves a patient from the database by its unique identifier id.
     *
     * @param id Is the unique identifier of the patient object.
     * @return The patient object itself.
     * @throws PatientNotFoundException If no patients with specified id is found in the database.
     */
    public Patient getPatientById(final Long id) throws PatientNotFoundException {
        log.info("Looking for patient with id= " + id + " in the DB.");
        //TODO: caching
        return patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException("Patient can not be found."));
    }

    /**
     * Method adds a new patient to the database based on the provided patient request DTO.
     * Request body is validated on controller layer.
     *
     * @param patientDto The patient request Dto containing information about the new patient.
     * @return A list of patient response DTOs after adding new patient.
     */
    public List<PatientResponseDto> addNewPatient(final PatientRequestDto patientDto) {
        Patient patient = mappingService.mapPatientToEntity(patientDto);
        patientRepository.save(patient);
        log.info("New patient was added.");
        List<Patient> allPatients = patientRepository.findAll();
        return mappingService.mapPatientsToResponse(allPatients);
    }

    /**
     * Method updates the information of an existing patient in database based on the provided update request.
     *
     * @param patientId            The unique identifier of the patient to be updated.
     * @param patientUpdateRequest The update request containing new information for the new information about existing patient.
     * @throws InvalidDataException     If the validator detects that provided update request contains invalid data or are the same as the old one.
     * @throws PatientUpdateException   If the patient update cannot be performed due to validation or other reasons.
     * @throws PatientNotFoundException If no patient with specified ID is found in the database.
     */
    @Transactional
    public void updatePatientInfo(final Long patientId, final PatientUpdateRequest patientUpdateRequest) throws InvalidDataException, PatientUpdateException, PatientNotFoundException {
        Patient patient = getPatientById(patientId);

        log.info("Validating given data for update.");
        Boolean validated = patientUpdateDataValidator.validateGivenDataForUpdate(patientUpdateRequest, patient);

        if (validated) {
            patient.setName(patientUpdateRequest.getName());
            patient.setLastName(patientUpdateRequest.getLastName());
            patient.setDob(patientUpdateRequest.getDob());
            patient.setContactInfo(patientUpdateRequest.getContactInfo());

            patientRepository.save(patient);
            log.info("Patient was updated successfully.");
            return;
        }
        throw new PatientUpdateException("Patient can not be updated.");
    }

    /**
     * Method deletes a patient from database by the specified id.
     *
     * @param id Is the unique identifier of the patient to be deleted.
     * @throws DeleteOperationException If an error occurs while deleting a patient from the database.
     */
    public void deletePatientById(final Long id) throws DeleteOperationException {
        try {
            log.info("Looking for patient with id = " + id + " in the DB.");
            patientRepository.deleteById(id);

        } catch (Exception e) {
            log.error("Failed to delete patient with id = " + id);
            throw new DeleteOperationException("Failed to delete patient. Try different id.");
        }
    }
}
