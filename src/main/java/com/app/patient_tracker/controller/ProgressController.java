package com.app.patient_tracker.controller;

import com.app.patient_tracker.dto.ProgressRequestDto;
import com.app.patient_tracker.exception.PatientNotFoundException;
import com.app.patient_tracker.service.ProgressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/progress")
@Slf4j
@RequiredArgsConstructor
public class ProgressController {

    private final ProgressService progressService;

    @PostMapping("/fillProgress/{id}")
    public ResponseEntity<?> fillPatientProgress(@PathVariable Long id, @RequestBody ProgressRequestDto progressRequestDto) throws PatientNotFoundException {
        progressService.fillProgress(id, progressRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body("Progress filled.");
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<?> getAllPatientProgresses(@PathVariable Long id) throws PatientNotFoundException {
        var progresses = progressService.getProgressesByPatientId(id);
        return ResponseEntity.status(HttpStatus.OK).body(progresses);
    }
}
