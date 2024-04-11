package com.app.patient_tracker.dto;


import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class AssessmentRequestDto {

    private String title;

    private Integer points;

    private Long patientId;
}
