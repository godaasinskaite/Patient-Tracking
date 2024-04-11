package com.app.patient_tracker.repository;

import com.app.patient_tracker.model.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssessmentRepository extends JpaRepository<Assessment, Long> {
}
