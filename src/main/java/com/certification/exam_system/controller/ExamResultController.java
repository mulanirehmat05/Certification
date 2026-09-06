package com.certification.exam_system.controller;

import com.certification.exam_system.dto.exam.ExamResultResponse;
import com.certification.exam_system.service.ExamResultService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/exam-results")
public class ExamResultController {

    private final ExamResultService examResultService;

    public ExamResultController(
            ExamResultService examResultService
    ) {
        this.examResultService = examResultService;
    }

    @GetMapping("/attempt/{attemptId}")
    public ResponseEntity<ExamResultResponse> getResultByAttemptId(
            @PathVariable Long attemptId
    ) {

        ExamResultResponse response =
                examResultService.getResultByAttemptId(attemptId);

        return ResponseEntity.ok(response);
    }
}