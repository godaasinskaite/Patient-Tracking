package com.app.patient_tracker.validator;

import com.app.patient_tracker.dto.AssessmentRequestDto;
import com.app.patient_tracker.exception.ApplicationException;
import com.app.patient_tracker.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AssessmentRequestValidator {

    public void validateAssessmentRequest(final AssessmentRequestDto assessmentRequestDto) throws ApplicationException {
        if (assessmentRequestDto == null) {
            log.error("Assessment request was null.");
            throw new ApplicationException("Assessment request was empty.", ErrorCode.ASSESSMENT_REQUEST_EXCEPTION);
        }
        validatePoints(assessmentRequestDto.getPoints());
        validateTitle(assessmentRequestDto.getTitle());
    }

    private void validatePoints(Integer points) throws ApplicationException {
        if (points == null || points < 0) {
            log.error("Assessment points is null or less than zero.");
            throw new ApplicationException("Assessment points is null or less than zero", ErrorCode.ASSESSMENT_REQUEST_EXCEPTION);
        }
    }

    private void validateTitle(String title) throws ApplicationException {
        if (title == null) {
            throw new ApplicationException("Missing assessment title.", ErrorCode.MANDATORY_FIELD_MISSING_EXCEPTION);
        }
    }
}
