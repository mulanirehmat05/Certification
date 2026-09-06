package com.certification.exam_system.controller;

import com.certification.exam_system.dto.exam.ExamResultResponse;
import com.certification.exam_system.service.ExamEvaluationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/exam-evaluations")
public class ExamEvaluationController {

    private final ExamEvaluationService examEvaluationService;

    public ExamEvaluationController(
            ExamEvaluationService examEvaluationService
    ) {
        this.examEvaluationService = examEvaluationService;
    }

    @PostMapping("/{attemptId}")
    public ResponseEntity<ExamResultResponse> evaluateExam(
            @PathVariable Long attemptId
    ) {

        ExamResultResponse result =
                examEvaluationService.evaluateExam(attemptId);

        return ResponseEntity.ok(result);
    }
}