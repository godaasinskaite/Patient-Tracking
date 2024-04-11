package com.app.patient_tracker.dto;

import com.app.patient_tracker.model.Patient;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProgressResponseDto {

    private Long id;

    private String notes;

    private Patient patient;
}
