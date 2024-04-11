package com.app.patient_tracker.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "attendance")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "didattend")
    private Boolean didAttend = false;

    @Column(nullable = false,
    name = "dateofattendance")
    private LocalDate dateOfAttendance;

    @ManyToOne (cascade = CascadeType.PERSIST)
    @JoinColumn(name = "patient_id")
    @JsonBackReference
    private Patient patient;
}
