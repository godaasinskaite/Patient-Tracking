package com.app.patient_tracker.controller;

import com.app.patient_tracker.dto.AttendanceRequestDto;
import com.app.patient_tracker.exception.AttendanceMappingException;
import com.app.patient_tracker.exception.AttendanceNotFoundException;
import com.app.patient_tracker.exception.MandatoryFieldsMissingException;
import com.app.patient_tracker.exception.PatientNotFoundException;
import com.app.patient_tracker.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/attendance")
@Slf4j
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllAttendances() throws AttendanceNotFoundException {
        var attendances = attendanceService.getAllAttendances();
        return ResponseEntity.status(HttpStatus.OK).body(attendances);
    }
    @PatchMapping("/markAttendance{patientId}/{attendanceId}")
    public ResponseEntity<?> markAttendance(@PathVariable Long patientId, @PathVariable Long attendanceId) throws PatientNotFoundException, AttendanceNotFoundException {
        attendanceService.markAttendance(patientId, attendanceId);
        return ResponseEntity.status(HttpStatus.OK).body("Attendance marked.");
    }

    @GetMapping("/schedule")
    public ResponseEntity<?> checkSchedule() throws AttendanceNotFoundException {
        var upcomingOccupationDates = attendanceService.checkSchedule();
        return ResponseEntity.status(HttpStatus.OK).body(upcomingOccupationDates);
    }

    @PostMapping("/add/{id}")
    public ResponseEntity<?> scheduleAppointment(@PathVariable Long id, @RequestBody AttendanceRequestDto attendanceRequestDto) throws AttendanceMappingException, PatientNotFoundException, MandatoryFieldsMissingException {
        var attendance = attendanceService.scheduleAppointment(attendanceRequestDto, id);
        return ResponseEntity.status(HttpStatus.OK).body(attendance);
    }
}
