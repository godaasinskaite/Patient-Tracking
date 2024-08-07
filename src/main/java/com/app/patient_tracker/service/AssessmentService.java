package com.app.patient_tracker.service;

import com.app.patient_tracker.dto.AssessmentRequestDto;
import com.app.patient_tracker.exception.*;
import com.app.patient_tracker.model.Assessment;
import com.app.patient_tracker.model.Patient;
import com.app.patient_tracker.repository.AssessmentRepository;
import com.app.patient_tracker.validator.AssessmentRequestValidator;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for managing assessment related operations.
 */
@Service
@Slf4j
@Data
@RequiredArgsConstructor
public class AssessmentService {

    private final AssessmentRepository assessmentRepository;
    private final PatientService patientService;
    private final AssessmentRequestValidator assessmentRequestValidator;
    private final AssessmentMappingService assessmentMappingService;

    /**
     * Method assesses a patient based on the provided assessment request data.
     * This method retrieved patient by its unique id, validates assessment request data to ensure that mandatory fields are present,
     * maps the assessment request data to an entity and saves assessment to DB.
     *
     * @param patientId            Is the id of the patient to be assessed.
     * @param assessmentRequestDto The assessment request containing information about the new assessment.
     * @return The assessment entity representing the assessment of the patient.
     * @throws ApplicationException If the patient with specified id is not found
     *                              or mandatory fields are missing in the assessment request.
     */
    public Assessment assessPatient(final Long patientId, final AssessmentRequestDto assessmentRequestDto) throws ApplicationException {
        final Patient patient = patientService.getPatientById(patientId);

        assessmentRequestValidator.validateAssessmentRequest(assessmentRequestDto);
        final Assessment newAssessment = assessmentMappingService.mapAttendanceToEntity(assessmentRequestDto);
        newAssessment.setPatient(patient);
        patient.getAssessments().add(newAssessment);

        assessmentRepository.save(newAssessment);
        log.info("New assessment added.");
        return newAssessment;
    }

    /**
     * Method to update existing assessment with specified id.
     * Retrieves the assessment by id from repository. If the assessment is found and both title and points are not nulls,
     * updates and saves the assessment with new data.
     *
     * @param id     The id of the assessment to update.
     * @param title  New title for the assessment.
     * @param points New points for the assessment.
     * @throws ApplicationException if Assessment with specified id can not be found
     *                              or Assessment can not be updated.
     */
    public void updateAssessment(final Long id, final String title, final Integer points) throws ApplicationException {
        log.info("Looking for assessment with id = " + id);
        final Assessment assessmentToUpdate = assessmentRepository.findById(id)
                .orElseThrow(() -> new ApplicationException("Assessment can not be found.", ErrorCode.ASSESSMENT_NOT_FOUND_EXCEPTION));

        if (title != null && points != null) {
            assessmentToUpdate.setTitle(title);
            assessmentToUpdate.setPoints(points);

            log.info("Assessment been updated.");
            assessmentRepository.save(assessmentToUpdate);
            return;
        }
        throw new ApplicationException("Assessment can not be updated.", ErrorCode.ASSESSMENT_UPDATE_EXCEPTION);
    }
}
