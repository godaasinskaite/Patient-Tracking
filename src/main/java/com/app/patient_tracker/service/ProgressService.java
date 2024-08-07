package com.app.patient_tracker.service;

import com.app.patient_tracker.dto.ProgressRequestDto;
import com.app.patient_tracker.exception.ApplicationException;
import com.app.patient_tracker.model.Patient;
import com.app.patient_tracker.model.Progress;
import com.app.patient_tracker.repository.ProgressRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class responsible for managing attendance related operations.
 */
@Service
@Slf4j
@Data
@RequiredArgsConstructor
public class ProgressService {

    private final ProgressRepository progressRepository;
    private final ProgressMappingService progressMappingService;
    private final PatientService patientService;

    /**
     * Method fills out progress information for a patient.
     *
     * @param patientId          Is the unique identifier of a patient for whom progress is being filled.
     * @param progressRequestDto DTO containing information to fill out progress.
     * @throws ApplicationException If patient is found with specified id.
     */
    public void fillProgress(final Long patientId, final ProgressRequestDto progressRequestDto) throws ApplicationException {
        final Patient patient = patientService.getPatientById(patientId);
        final Progress progress = progressMappingService.mapProgressToEntity(progressRequestDto);
        progress.setPatient(patient);
        patient.getPatientProgress().add(progress);
        progressRepository.save(progress);
    }

    /**
     * Method retrieved all progress records associated with patient.
     *
     * @param id Is the unique identifier of a patient which progresses will be retrieved.
     * @return A list of progress records associated with the patient.
     * @throws ApplicationException If patient with specified id is found.
     */
    public List<Progress> getProgressesByPatientId(final Long id) throws ApplicationException {
        final Patient patient = patientService.getPatientById(id);
        return patient.getPatientProgress();
    }
}
