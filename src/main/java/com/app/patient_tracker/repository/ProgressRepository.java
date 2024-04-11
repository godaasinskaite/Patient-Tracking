package com.app.patient_tracker.repository;

import com.app.patient_tracker.model.Progress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgressRepository extends JpaRepository<Progress, Long> {
}
