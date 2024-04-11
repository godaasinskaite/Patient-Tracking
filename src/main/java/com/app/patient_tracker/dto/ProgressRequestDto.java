package com.app.patient_tracker.dto;

import com.app.patient_tracker.model.Patient;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProgressRequestDto {

    private String notes;
    private Long patientId;
}
