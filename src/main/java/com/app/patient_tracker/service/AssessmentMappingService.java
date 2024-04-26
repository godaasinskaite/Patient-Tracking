package com.app.patient_tracker.service;

import com.app.patient_tracker.dto.AssessmentRequestDto;
import com.app.patient_tracker.model.Assessment;
import org.springframework.stereotype.Service;

@Service
public class AssessmentMappingService {

    /**
     * Method to map api assessment request to an entity suitable for database storage.
     * Creates new Assessment entity using the data from DTO request.
     * @param assessmentRequestDto represents request class containing data.
     * @return The mapped Assessment entity.
     */
    public Assessment mapAttendanceToEntity(final AssessmentRequestDto assessmentRequestDto) {
        return Assessment.builder()
                .title(assessmentRequestDto.getTitle())
                .points(assessmentRequestDto.getPoints())
                .build();
    }
}
