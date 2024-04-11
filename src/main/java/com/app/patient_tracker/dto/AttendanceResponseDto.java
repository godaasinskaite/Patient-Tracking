package com.app.patient_tracker.dto;

import com.app.patient_tracker.model.Patient;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class AttendanceResponseDto {

    private Long id;

    private Boolean didAttend;

    private LocalDate dateOfAttendance;

    private Patient patient;
}
