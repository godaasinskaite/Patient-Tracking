package com.app.patient_tracker.repository;

import com.app.patient_tracker.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
}
