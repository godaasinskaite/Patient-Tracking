package com.app.patient_tracker.dto;

import com.app.patient_tracker.model.Assessment;
import com.app.patient_tracker.model.Attendance;
import com.app.patient_tracker.model.Progress;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class PatientRequestDto {

    private String name;

    private String lastName;

    private LocalDate dob;

    private String contactInfo;

    private List<Attendance> attendances;

    private List<Assessment> assessments;

    private List<Progress> patientProgress;

    private LocalDate nextAppointment;
}
