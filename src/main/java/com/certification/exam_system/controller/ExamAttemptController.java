package com.certification.exam_system.controller;

import com.certification.exam_system.dto.exam.ExamAttemptRequest;
import com.certification.exam_system.dto.exam.ExamAttemptResponse;
import com.certification.exam_system.service.ExamAttemptService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/exam-attempts")
public class ExamAttemptController {

    private final ExamAttemptService examAttemptService;

    public ExamAttemptController(
            ExamAttemptService examAttemptService
    ) {
        this.examAttemptService = examAttemptService;
    }

    @PostMapping("/start")
    public ResponseEntity<ExamAttemptResponse> startExam(
            @Valid @RequestBody ExamAttemptRequest request
    ) {

        ExamAttemptResponse response =
                examAttemptService.startExam(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{attemptId}/questions")
    public ResponseEntity<?> getExamQuestions(
            @PathVariable Long attemptId
    ) {

        return ResponseEntity.ok(
                examAttemptService.getExamQuestions(attemptId)
        );
    }
}