package com.app.patient_tracker.repository;

import com.app.patient_tracker.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    //TODO: Think of custom SQL methods
}
