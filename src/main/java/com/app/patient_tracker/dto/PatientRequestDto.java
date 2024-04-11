package com.app.patient_tracker.dto;

import com.app.patient_tracker.model.Assessment;
import com.app.patient_tracker.model.Attendance;
import com.app.patient_tracker.model.Progress;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class PatientRequestDto {

    private String name;

    private String lastName;

    private LocalDate dob;

    private String contactInfo;

    private List<Attendance> attendances = new ArrayList<>();

    private List<Assessment> assessments = new ArrayList<>();

    private List<Progress> patientProgress = new ArrayList<>();

    private LocalDate nextAppointment;
}
