package com.app.patient_tracker.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class AttendanceRequestDto {
    private LocalDate dateOfAttendance;

    private Long patientId;
}
