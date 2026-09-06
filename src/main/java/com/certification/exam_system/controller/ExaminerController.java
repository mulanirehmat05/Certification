package com.certification.exam_system.controller;

import com.certification.exam_system.dto.ExaminerResponse;
import com.certification.exam_system.service.ExaminerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/examiners")
public class ExaminerController {

    private final ExaminerService examinerService;

    public ExaminerController(
            ExaminerService examinerService
    ) {
        this.examinerService = examinerService;
    }

    @GetMapping
    public ResponseEntity<List<ExaminerResponse>> getAllExaminers() {

        return ResponseEntity.ok(
                examinerService.getAllExaminers()
        );
    }

    @GetMapping("/active")
    public ResponseEntity<List<ExaminerResponse>> getActiveExaminers() {

        return ResponseEntity.ok(
                examinerService.getActiveExaminers()
        );
    }
}