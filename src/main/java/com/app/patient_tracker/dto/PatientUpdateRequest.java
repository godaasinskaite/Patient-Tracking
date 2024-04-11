package com.app.patient_tracker.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class PatientUpdateRequest {

    private String name;

    private String lastName;

    private LocalDate dob;

    private String contactInfo;
}
