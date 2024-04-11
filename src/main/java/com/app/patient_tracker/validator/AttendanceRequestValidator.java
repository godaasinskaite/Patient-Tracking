package com.app.patient_tracker.validator;

import com.app.patient_tracker.dto.AttendanceRequestDto;
import com.app.patient_tracker.exception.MandatoryFieldsMissingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Slf4j
public class AttendanceRequestValidator {

    public void validateAttendanceRequest(final AttendanceRequestDto attendanceRequestDto) throws MandatoryFieldsMissingException {
        if (attendanceRequestDto == null || attendanceRequestDto.getDateOfAttendance().isBefore(LocalDate.now())) {
            log.error("Attendance request was empty, mandatory fields missing or wrong date was give.");
            throw new MandatoryFieldsMissingException("Mandatory fields missing or they are incorrect.");
        }
    }
}
