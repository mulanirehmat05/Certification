package com.certification.exam_system.controller;

import com.certification.exam_system.dto.ExaminerResultResponse;
import com.certification.exam_system.service.ExaminerResultService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/examiner-results")
public class ExaminerResultController {

    private final ExaminerResultService examinerResultService;

    public ExaminerResultController(
            ExaminerResultService examinerResultService
    ) {
        this.examinerResultService = examinerResultService;
    }

    @GetMapping("/attempt/{attemptId}")
    public ResponseEntity<ExaminerResultResponse> getAttemptResult(
            @PathVariable Long attemptId
    ) {

        return ResponseEntity.ok(
                examinerResultService.getAttemptResult(attemptId)
        );
    }
}