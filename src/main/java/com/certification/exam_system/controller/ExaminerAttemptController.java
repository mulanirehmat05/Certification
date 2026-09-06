package com.certification.exam_system.controller;

import com.certification.exam_system.dto.ExaminerAttemptResponse;
import com.certification.exam_system.dto.ExaminerSessionSummaryResponse;
import com.certification.exam_system.service.ExaminerAttemptService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/examiner-attempts")
public class ExaminerAttemptController {

    private final ExaminerAttemptService examinerAttemptService;

    public ExaminerAttemptController(
            ExaminerAttemptService examinerAttemptService
    ) {
        this.examinerAttemptService = examinerAttemptService;
    }

    @GetMapping("/session/{examSessionId}")
    public ResponseEntity<List<ExaminerAttemptResponse>> getSessionAttempts(
            @PathVariable Long examSessionId
    ) {

        return ResponseEntity.ok(
                examinerAttemptService.getSessionAttempts(examSessionId)
        );
    }

    @GetMapping("/session/{examSessionId}/summary")
    public ResponseEntity<ExaminerSessionSummaryResponse> getSessionSummary(
            @PathVariable Long examSessionId
    ) {

        return ResponseEntity.ok(
                examinerAttemptService.getSessionSummary(examSessionId)
        );
    }
}