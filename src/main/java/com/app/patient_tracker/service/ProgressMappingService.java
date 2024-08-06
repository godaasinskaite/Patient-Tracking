package com.app.patient_tracker.service;

import com.app.patient_tracker.dto.ProgressRequestDto;
import com.app.patient_tracker.model.Progress;
import org.springframework.stereotype.Service;


@Service
public class ProgressMappingService {

    /**
     * Method to map api progress request to an entity suitable for database storage.
     * Creates new Progress entity using the data from DTO request.
     *
     * @param progressRequestDto represents request class containing data.
     * @return The mapped Progress entity.
     */
    public Progress mapProgressToEntity(final ProgressRequestDto progressRequestDto) {
        return Progress.builder()
                .notes(progressRequestDto.getNotes())
                .build();
    }
}
