package com.app.patient_tracker.service;

import com.app.patient_tracker.dto.PatientUpdateRequest;
import com.app.patient_tracker.exception.ApplicationException;
import com.app.patient_tracker.model.Assessment;
import com.app.patient_tracker.model.Attendance;
import com.app.patient_tracker.model.Patient;
import com.app.patient_tracker.model.Progress;
import com.app.patient_tracker.repository.PatientRepository;
import com.app.patient_tracker.validator.PatientUpdateRequestValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock
    private PatientRepository repository;
    @Mock
    private CacheManager cacheManager;
    @Mock
    private Cache cache;
    @Mock
    private PatientMappingService mappingService;
    @InjectMocks
    private PatientService patientService;
    @Mock
    private PatientUpdateRequestValidator patientUpdateRequestValidator;


    @Test
    void checkForNextAppointment() {
        Patient patient = loadTestData().get(2);
        when(repository.save(Mockito.any(Patient.class))).thenReturn(patient);
        patientService.checkForNextAppointment(patient);
        Assertions.assertEquals(LocalDate.of(2043, 4, 1), patient.getNextAppointment());
    }

    @Test
    void getAllPatients() throws ApplicationException {
        List<Patient> patients = loadTestData();
        Mockito.when(repository.findAll()).thenReturn(patients);
        List<Patient> patientsFromDb = patientService.getAllPatients();
        Assertions.assertNotNull(patientsFromDb);
    }

    @Test
    void getPatientById() throws ApplicationException {
        Patient patient = loadTestData().get(0);
        Mockito.when(repository.findById(patient.getId())).thenReturn(Optional.of(patient));
        Patient retrievedPatient = patientService.getPatientById(patient.getId());
        Assertions.assertNotNull(retrievedPatient);
    }

    @Test
    void updatePatientInfo() throws ApplicationException, IllegalAccessException {
        Patient patient = loadTestData().get(0);
        PatientUpdateRequest updateRequest = PatientUpdateRequest.builder().contactInfo("1234").name("1234").build();

        Mockito.when(repository.findById(patient.getId())).thenReturn(Optional.of(patient));
//        Mockito.when(patientUpdateRequestValidator.validateGivenDataForUpdate(updateRequest, patient)).thenReturn(true);
        Mockito.when(repository.save(patient)).thenReturn(patient);

        patientService.updatePatientInfo(patient.getId(), updateRequest);

        Assertions.assertEquals("1234", patient.getContactInfo());
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

        Attendance attendance1 = Attendance.builder().didAttend(true).dateOfAttendance(LocalDate.of(2034, 2, 1)).patient(jim).build();
        Attendance attendance2 = Attendance.builder().didAttend(false).dateOfAttendance(LocalDate.of(2034, 3, 1)).patient(jim).build();
        Attendance attendance3 = Attendance.builder().didAttend(true).dateOfAttendance(LocalDate.of(2034, 1, 15)).patient(dwight).build();
        Attendance attendance4 = Attendance.builder().didAttend(false).dateOfAttendance(LocalDate.of(2034, 4, 20)).patient(dwight).build();
        Attendance attendance5 = Attendance.builder().didAttend(false).dateOfAttendance(LocalDate.of(2043, 4, 1)).patient(pam).build();
        Attendance attendance6 = Attendance.builder().didAttend(false).dateOfAttendance(LocalDate.of(2034, 5, 1)).patient(pam).build();
        Attendance attendance7 = Attendance.builder().didAttend(true).dateOfAttendance(LocalDate.of(2034, 2, 1)).patient(pam).build();

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