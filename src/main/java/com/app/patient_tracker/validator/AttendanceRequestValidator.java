package com.app.patient_tracker.validator;

import com.app.patient_tracker.dto.AttendanceRequestDto;
import com.app.patient_tracker.exception.ApplicationException;
import com.app.patient_tracker.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Slf4j
public class AttendanceRequestValidator {

    public void validateAttendanceRequest(final AttendanceRequestDto attendanceRequestDto) throws ApplicationException {
        if (attendanceRequestDto == null) {
            log.error("Attendance request was empty.");
            throw new ApplicationException("Attendance request is empty.", ErrorCode.ATTENDANCE_REQUEST_EXCEPTION);
        }
        validateAttendanceDate(attendanceRequestDto.getDateOfAttendance());
    }

    private void validateAttendanceDate(LocalDate date) throws ApplicationException {
        if (date.isBefore(LocalDate.now())) {
            throw new ApplicationException("Wrong attendance date.", ErrorCode.ATTENDANCE_REQUEST_EXCEPTION);
        }
    }
}
