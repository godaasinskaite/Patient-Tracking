package com.app.patient_tracker.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false,
    name = "lastname")
    private String lastName;

    @Column(nullable = false)
    private LocalDate dob;

    @Column(nullable = false,
    name = "contactinfo")
    private String contactInfo;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.PERSIST)
    @JsonManagedReference
    private List<Attendance> attendances = new ArrayList<>();

    @OneToMany(mappedBy = "patient", cascade = CascadeType.PERSIST)
    @JsonManagedReference
    private List<Assessment> assessments = new ArrayList<>();

    @OneToMany(mappedBy = "patient", cascade = CascadeType.PERSIST)
    @JsonManagedReference
    private List<Progress> patientProgress = new ArrayList<>();

    @Column (name = "nextappointment")
    private LocalDate nextAppointment;

    @Override
    public String toString() {
        return "Patient{" +
                "name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dob=" + dob +
                ", contactInfo='" + contactInfo + '\'' +
                '}';
    }

    //    attendances.stream()
//                .filter(attendance -> !attendance.getDidAttend() && attendance.getDateOfAttendance().isAfter(LocalDate.now()))
//                .map(Attendance::getDateOfAttendance)
//                .findFirst().orElse(null);
}
