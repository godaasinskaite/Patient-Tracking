package com.app.patient_tracker.service;

import com.app.patient_tracker.dto.*;
import com.app.patient_tracker.exception.*;
import com.app.patient_tracker.model.Attendance;
import com.app.patient_tracker.model.Patient;
import com.app.patient_tracker.repository.PatientRepository;
import com.app.patient_tracker.validator.PatientRequestValidator;
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
    private final PatientRequestValidator patientRequestValidator;

    public PatientService(PatientRepository patientRepository, CacheManager cacheManager, PatientMappingService mappingService, PatientUpdateRequestValidator patientUpdateDataValidator, PatientRequestValidator patientRequestValidator) {
        this.patientRepository = patientRepository;
        this.cacheManager = cacheManager;
        this.cache = cacheManager.getCache("cache");
        this.mappingService = mappingService;
        this.patientUpdateDataValidator = patientUpdateDataValidator;
        this.patientRequestValidator = patientRequestValidator;
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
        final LocalDate localDate = patient.getAttendances().stream()
                .filter(attendance -> !attendance.getDidAttend() && attendance.getDateOfAttendance().isAfter(LocalDate.now()))
                .map(Attendance::getDateOfAttendance)
                .findFirst()
                .orElse(null);

        patient.setNextAppointment(localDate);
        patientRepository.save(patient);
        log.info("Patient nextAppointment updated");
    }

    /**
     * Method retrieves a list of patients that were found in database.
     *
     * @return A list containing all patients stored in the database.
     * @throws ApplicationException If the patient list is empty in the DB.
     */
    public List<Patient> getAllPatients() throws ApplicationException {
        log.info("Looking for patients in DB.");
        final List<Patient> patients = patientRepository.findAll();

        if (patients.isEmpty()) {
            log.error("DB is empty. No patients were found.");
            throw new ApplicationException("No patients were found.", ErrorCode.PATIENT_NOT_FOUND_EXCEPTION);
        }

        log.info(patients.size() + " patients were found in the DB.");
        return patients;
    }

    /**
     * Method retrieves a patient from the database by its unique identifier id.
     *
     * @param id Is the unique identifier of the patient object.
     * @return The patient object itself.
     * @throws ApplicationException If patient with specified id can not be found in the database.
     */
    public Patient getPatientById(final Long id) throws ApplicationException {
        log.info("Looking for patient with id= " + id + " in the DB.");
        //TODO: caching
        return patientRepository.findById(id)
                .orElseThrow(() -> new ApplicationException("Patient with id = " + id + " can not be found.", ErrorCode.PATIENT_NOT_FOUND_EXCEPTION));
    }

    /**
     * Method validates given dto and adds a new patient to the database based on the provided patient request DTO.
     *
     * @param patientDto The patient request Dto containing information about the new patient.
     * @return A list of patient response DTOs after adding new patient.
     * @throws ApplicationException if no patients can not be found in the database.
     */
    public List<PatientResponseDto> addNewPatient(final PatientRequestDto patientDto) throws ApplicationException {
        patientRequestValidator.validatePatientRequest(patientDto);
        final Patient patient = mappingService.mapPatientToEntity(patientDto);
        patientRepository.save(patient);
        log.info("New patient was added.");

        final List<Patient> allPatients = patientRepository.findAll();
        return mappingService.mapPatientsToResponse(allPatients);
    }

    /**
     * Method updates the information of an existing patient in database based on the provided update request.
     *
     * @param patientId            The unique identifier of the patient to be updated.
     * @param patientUpdateRequest The update request containing new information for the new information about existing patient.
     * @throws ApplicationException If no patient with specified ID is found in the database.
     *                              or if PatientUpdateRequest does not pass the validation.
     */
    public void updatePatientInfo(final Long patientId, final PatientUpdateRequest patientUpdateRequest) throws ApplicationException, IllegalAccessException {
        final Patient patient = getPatientById(patientId);

        patientUpdateDataValidator.validateGivenDataForUpdate(patientUpdateRequest, patient);
        patient.setName(patientUpdateRequest.getName());
        patient.setLastName(patientUpdateRequest.getLastName());
        patient.setDob(patientUpdateRequest.getDob());
        patient.setContactInfo(patientUpdateRequest.getContactInfo());

        patientRepository.save(patient);
        log.info("Patient was updated successfully.");
    }

    /**
     * Method deletes a patient from database by the specified id.
     *
     * @param id Is the unique identifier of the patient to be deleted.
     * @throws ApplicationException If an error occurs while deleting a patient from the database.
     */
    public void deletePatientById(final Long id) throws ApplicationException {
        try {
            log.info("Looking for patient with id = " + id + " in the DB.");
            patientRepository.deleteById(id);

        } catch (Exception e) {
            log.error("Failed to delete patient with id = " + id);
            throw new ApplicationException("Failed to delete patient. Try different id.", ErrorCode.DELETE_OPERATION_EXCEPTION);
        }
    }
}
