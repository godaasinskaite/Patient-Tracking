package com.app.patient_tracker.controller;

import com.app.patient_tracker.dto.AttendanceRequestDto;
import com.app.patient_tracker.exception.AttendanceMappingException;
import com.app.patient_tracker.exception.AttendanceNotFoundException;
import com.app.patient_tracker.exception.MandatoryFieldsMissingException;
import com.app.patient_tracker.exception.PatientNotFoundException;
import com.app.patient_tracker.model.Attendance;
import com.app.patient_tracker.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/attendance")
@Slf4j
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllAttendances() throws AttendanceNotFoundException {
        List<Attendance> attendances = attendanceService.getAllAttendances();
        return ResponseEntity.status(HttpStatus.OK).body(attendances);
    }
    @PatchMapping("/{attendanceId}")
    public ResponseEntity<?> markAttendance(@PathVariable final Long attendanceId) throws PatientNotFoundException, AttendanceNotFoundException {
        attendanceService.markAttendance(attendanceId);
        return ResponseEntity.status(HttpStatus.OK).body("Attendance marked.");
    }

    @GetMapping("/schedule")
    public ResponseEntity<?> checkSchedule() throws AttendanceNotFoundException {
        List<LocalDate> upcomingOccupationDates = attendanceService.checkSchedule();
        return ResponseEntity.status(HttpStatus.OK).body(upcomingOccupationDates);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> scheduleAppointment(@PathVariable final Long PatientId, @RequestBody final AttendanceRequestDto attendanceRequestDto) throws AttendanceMappingException, PatientNotFoundException, MandatoryFieldsMissingException {
        Attendance attendance = attendanceService.scheduleAppointment(attendanceRequestDto, PatientId);
        return ResponseEntity.status(HttpStatus.OK).body(attendance);
    }
}
