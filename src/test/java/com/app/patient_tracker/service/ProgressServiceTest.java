package com.app.patient_tracker.service;

import com.app.patient_tracker.dto.ProgressRequestDto;
import com.app.patient_tracker.exception.PatientNotFoundException;
import com.app.patient_tracker.model.Assessment;
import com.app.patient_tracker.model.Attendance;
import com.app.patient_tracker.model.Patient;
import com.app.patient_tracker.model.Progress;
import com.app.patient_tracker.repository.ProgressRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@ExtendWith(MockitoExtension.class)
class ProgressServiceTest {

    @Mock
    private ProgressRepository progressRepository;
    @InjectMocks
    private ProgressService progressService;
    @Mock
    private PatientService patientService;
    @Mock
    private ProgressMappingService progressMappingService;

    @Test
    void fillProgress() throws PatientNotFoundException {
        Patient patient = loadTestData().get(0);
        ProgressRequestDto progressRequest = ProgressRequestDto.builder().build();
        Progress newProgress = Progress.builder().build();

        Mockito.when(patientService.getPatientById(patient.getId())).thenReturn(patient);
        Mockito.when(progressMappingService.mapProgressToEntity(progressRequest)).thenReturn(newProgress);
        Mockito.when(progressRepository.save(newProgress)).thenReturn(newProgress);

        progressService.fillProgress(patient.getId(), progressRequest);

        Assertions.assertEquals(3, patient.getPatientProgress().size());
    }

    @Test
    void getProgressesByPatientId() throws PatientNotFoundException {
        Patient patient = loadTestData().get(0);
        Mockito.when(patientService.getPatientById(patient.getId())).thenReturn(patient);
        progressService.getProgressesByPatientId(patient.getId());
        Assertions.assertEquals(2, patient.getPatientProgress().size());
    }


    List<Patient> loadTestData() {
        Patient jim = Patient.builder().name("Jim").lastName("Halpert").contactInfo("jim.halper@mail.com").dob(LocalDate.of(1980, 10, 10)).build();
        Patient dwight = Patient.builder().name("Dwight").lastName("Schrute").contactInfo("schrute@mail.com").dob(LocalDate.of(1975, 5, 7)).build();
        Patient pam = Patient.builder().name("Pam").lastName("Beesley").contactInfo("lovely.pam@mail.com").dob(LocalDate.of(1982, 9, 30)).build();

        Assessment assessment1 = Assessment.builder().title("Provided Service Title").points(45).patient(jim).build();
        Assessment assessment2 = Assessment.builder().title("Provided Service Title2").points(56).patient(jim).build();
        Assessment assessment3 = Assessment.builder().title("Provided Service Title3").points(89).patient(dwight).build();
        Assessment assessment4 = Assessment.builder().title("Provided Service Title4").points(95).patient(dwight).build();
        Assessment assessment5 = Assessment.builder().title("Provided Service Title5").points(12).patient(dwight).build();
        Assessment assessment6 = Assessment.builder().title("Provided Service Title6").points(70).patient(dwight).build();

        List<Assessment> jimAssessments = new ArrayList<>();
        jimAssessments.add(assessment1);
        jimAssessments.add(assessment2);
        jim.setAssessments(jimAssessments);

        List<Assessment> dwightAssessments = new ArrayList<>();
        dwightAssessments.add(assessment3);
        dwightAssessments.add(assessment4);
        dwight.setAssessments(dwightAssessments);

        List<Assessment> pamAssessments = new ArrayList<>();
        pamAssessments.add(assessment5);
        pamAssessments.add(assessment6);
        pam.setAssessments(pamAssessments);

        Attendance attendance1 = Attendance.builder().didAttend(true).dateOfAttendance(LocalDate.of(2024, 2, 1)).patient(jim).build();
        Attendance attendance2 = Attendance.builder().didAttend(false).dateOfAttendance(LocalDate.of(2024, 3, 1)).patient(jim).build();
        Attendance attendance3 = Attendance.builder().didAttend(true).dateOfAttendance(LocalDate.of(2024, 1, 15)).patient(dwight).build();
        Attendance attendance4 = Attendance.builder().didAttend(false).dateOfAttendance(LocalDate.of(2024, 4, 20)).patient(dwight).build();
        Attendance attendance5 = Attendance.builder().didAttend(false).dateOfAttendance(LocalDate.of(2024, 4, 1)).patient(pam).build();
        Attendance attendance6 = Attendance.builder().didAttend(false).dateOfAttendance(LocalDate.of(2024, 5, 1)).patient(pam).build();
        Attendance attendance7 = Attendance.builder().didAttend(true).dateOfAttendance(LocalDate.of(2024, 2, 1)).patient(pam).build();

        List<Attendance> jimAttendance = new ArrayList<>();
        jimAttendance.add(attendance1);
        jimAttendance.add(attendance2);
        jim.setAttendances(jimAttendance);

        List<Attendance> dwightAttendance = new ArrayList<>();
        dwightAttendance.add(attendance3);
        dwightAttendance.add(attendance4);
        dwight.setAttendances(dwightAttendance);

        List<Attendance> pamAttendance = new ArrayList<>();
        pamAttendance.add(attendance5);
        pamAttendance.add(attendance6);
        pamAttendance.add(attendance7);
        pam.setAttendances(pamAttendance);

        Progress progress1 = Progress.builder().notes("Small range of motion of knee flexion.").patient(jim).build();
        Progress progress2 = Progress.builder().notes("Range of motion in knee flexion got better.").patient(jim).build();
        Progress progress3 = Progress.builder().notes("Stiffness in neck.").patient(dwight).build();
        Progress progress4 = Progress.builder().notes("Patient has no problems anymore.").patient(dwight).build();
        Progress progress5 = Progress.builder().notes("Poor independence skills.").patient(pam).build();
        Progress progress6 = Progress.builder().notes("After environment adaptation, patient is quite independent.").patient(pam).build();

        List<Progress> jimProgress = new ArrayList<>();
        jimProgress.add(progress1);
        jimProgress.add(progress2);
        jim.setPatientProgress(jimProgress);

        List<Progress> dwightProgress = new ArrayList<>();
        dwightProgress.add(progress3);
        dwightProgress.add(progress4);
        dwight.setPatientProgress(dwightProgress);

        List<Progress> pamProgress = new ArrayList<>();
        pamProgress.add(progress5);
        pamProgress.add(progress6);
        pam.setPatientProgress(pamProgress);

        return Arrays.asList(jim, dwight, pam);
    }
}