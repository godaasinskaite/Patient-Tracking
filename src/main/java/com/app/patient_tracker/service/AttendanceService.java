package com.app.patient_tracker.service;

import com.app.patient_tracker.dto.AttendanceRequestDto;
import com.app.patient_tracker.exception.AttendanceMappingException;
import com.app.patient_tracker.exception.AttendanceNotFoundException;
import com.app.patient_tracker.exception.MandatoryFieldsMissingException;
import com.app.patient_tracker.exception.PatientNotFoundException;
import com.app.patient_tracker.model.Attendance;
import com.app.patient_tracker.model.Patient;
import com.app.patient_tracker.repository.AttendanceRepository;
import com.app.patient_tracker.repository.PatientRepository;
import com.app.patient_tracker.validator.AttendanceRequestValidator;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class responsible for managing attendance related operations.
 */
@Service
@Data
@Slf4j
@RequiredArgsConstructor
public class AttendanceService {

    private final PatientRepository patientRepository;
    private final AttendanceRepository attendanceRepository;
    private final AttendanceRequestValidator attendanceRequestValidator;
    private final AttendanceMappingService attendanceMappingService;
    private final PatientService patientService;

    /**
     * Method retrieved attendance record from the database by given its unique identifier id.
     *
     * @param id Is the unique identifier of the attendance record.
     * @return The attendance object itself with specified id.
     * @throws AttendanceNotFoundException If no attendance record is present by given id.
     */
    public Attendance findAttendanceById(final Long id) throws AttendanceNotFoundException {
        log.info("Looking for attendance with id = " + id);
        return attendanceRepository.findById(id)
                .orElseThrow(() -> new AttendanceNotFoundException("Attendance can not be found."));
    }

    /**
     * Method intended for marking the attendance of a patients specific appointment and updating patients next appointment field.
     *
     * @param attendanceToUpdateId Is the unique identifier of the attendance record to be updated.
     * @throws AttendanceNotFoundException If no attendance record is found with specified id.
     */
    public void markAttendance(final Long attendanceToUpdateId) throws AttendanceNotFoundException {
        final Attendance attendanceToUpdate = findAttendanceById(attendanceToUpdateId);
        final Patient patient = attendanceToUpdate.getPatient();

        attendanceToUpdate.setDateOfAttendance(LocalDate.now());
        attendanceToUpdate.setDidAttend(true);
        attendanceRepository.save(attendanceToUpdate);
        log.info("Attendance successfully updated.");

        patientService.checkForNextAppointment(patient);
    }

    /**
     * Method retrieved all attendance records from database.
     *
     * @return A list containing all attendance stored in database.
     * @throws AttendanceNotFoundException If no attendance record with specified id is found.
     */
    public List<Attendance> getAllAttendances() throws AttendanceNotFoundException {
        log.info("Looking for attendances in the DB.");
        final List<Attendance> attendances = attendanceRepository.findAll();

        if (attendances.isEmpty()) {
            log.error("No attendances were found in the DB.");
            throw new AttendanceNotFoundException("No attendances were found.");
        }

        log.info(attendances.size() + " attendances were found in the DB.");
        return attendances;
    }

    /**
     * Method checks the schedule for unattended appointments occurring after current date.
     *
     * @return A list of LocalDate objects representing dates of unattended appointments.
     * @throws AttendanceNotFoundException If no attendance records can be detected in database.
     */
    public List<LocalDate> checkSchedule() throws AttendanceNotFoundException {
        final List<Attendance> attendances = getAllAttendances();
        final LocalDate today = LocalDate.now();

        return attendances.stream()
                .filter(attendance -> !attendance.getDidAttend() && attendance.getDateOfAttendance().isAfter(today))
                .map(Attendance::getDateOfAttendance)
                .collect(Collectors.toList());
    }

    /**
     * Method schedules an appointment for patient based on the validated attendance request data.
     * This method retrieved patient by id, maps the attendance request data to an entity,
     * saves the attendance and updates patient attendances.
     *
     * @param attendanceRequestDto The attendance request data containing
     *                             information about the appointment.
     * @param id                   Is the unique identifier of patient.
     * @return The attendance entity representing scheduled appointment.
     * @throws AttendanceMappingException If an error occurs while mapping the attendance
     *                                    request data to an entity or saving the attendance.
     */
    public Attendance scheduleAppointment(final AttendanceRequestDto attendanceRequestDto, final Long id) throws AttendanceMappingException, PatientNotFoundException, MandatoryFieldsMissingException {
        final Patient patientToUpdate = patientService.getPatientById(id);

        if (!attendanceRequestValidator.validateAttendanceRequest(attendanceRequestDto)) {
            throw new AttendanceMappingException("Error mapping attendance");
        }
        final Attendance attendance = attendanceMappingService.mapAttendanceToEntity(attendanceRequestDto);
        attendance.setPatient(patientToUpdate);
        attendance.setDidAttend(false);
        attendanceRepository.save(attendance);

        patientToUpdate.getAttendances().add(attendance);
        patientService.checkForNextAppointment(patientToUpdate);
        log.info("New attendance added to patient.");
        return attendance;
    }
}
