package com.app.patient_tracker.controller;

import com.app.patient_tracker.dto.AssessmentRequestDto;
import com.app.patient_tracker.exception.AssessmentNotFoundException;
import com.app.patient_tracker.exception.AssessmentUpdateException;
import com.app.patient_tracker.exception.MandatoryFieldsMissingException;
import com.app.patient_tracker.exception.PatientNotFoundException;
import com.app.patient_tracker.model.Assessment;
import com.app.patient_tracker.service.AssessmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/assessment")
@Slf4j
@RequiredArgsConstructor
public class AssessmentController {

    private final AssessmentService assessmentService;

    @PostMapping("/{id}")
    public ResponseEntity<?> assessPatient(@PathVariable final Long id, @RequestBody final AssessmentRequestDto assessmentRequestDto) throws MandatoryFieldsMissingException, PatientNotFoundException {
        Assessment assessment = assessmentService.assessPatient(id, assessmentRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(assessment);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateAssessment(@PathVariable final Long id,
                                              @RequestParam (required = false) final String title,
                                              @RequestParam (required = false) final Integer points) throws AssessmentUpdateException, AssessmentNotFoundException {
        assessmentService.updateAssessment(id, title, points);
        return ResponseEntity.status(HttpStatus.OK).body("Assessment updated.");
    }
}
