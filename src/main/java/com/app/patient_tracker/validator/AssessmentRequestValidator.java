package com.app.patient_tracker.validator;

import com.app.patient_tracker.dto.AssessmentRequestDto;
import com.app.patient_tracker.exception.MandatoryFieldsMissingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AssessmentRequestValidator {

    public void validateAssessmentRequest(final AssessmentRequestDto assessmentRequestDto) throws MandatoryFieldsMissingException {
        if (assessmentRequestDto == null || assessmentRequestDto.getPoints() == null || assessmentRequestDto.getTitle() == null) {
            log.error("Assessment request was empty or mandatory fields were missing");
            throw new MandatoryFieldsMissingException("Mandatory fields are missing.");
        }
    }
}
