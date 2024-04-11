package com.app.patient_tracker.service;

import com.app.patient_tracker.dto.AttendanceRequestDto;
import com.app.patient_tracker.model.Attendance;
import org.springframework.stereotype.Service;


@Service
public class AttendanceMappingService {

    /**
     * Method to map api attendance request to an entity suitable for database storage.
     * Creates new Attendance entity using the data from DTO request.
     * @param attendanceDto represents request class containing data.
     * @return The mapped Attendance entity.
     */
    public Attendance mapAttendanceToEntity(final AttendanceRequestDto attendanceDto) {
        return Attendance.builder()
                .dateOfAttendance(attendanceDto.getDateOfAttendance())
                .build();
    }
}
