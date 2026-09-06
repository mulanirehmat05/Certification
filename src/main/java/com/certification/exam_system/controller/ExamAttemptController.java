package com.certification.exam_system.controller;

import com.certification.exam_system.dto.exam.ExamAttemptRequest;
import com.certification.exam_system.dto.exam.ExamAttemptResponse;
import com.certification.exam_system.dto.exam.CandidateExamQuestionResponse;
import com.certification.exam_system.dto.exam.ExamSubmitRequest;
import com.certification.exam_system.dto.exam.ExamSubmitResponse;
import com.certification.exam_system.service.ExamAttemptService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<CandidateExamQuestionResponse>> getExamQuestions(
            @PathVariable Long attemptId
    ) {

        List<CandidateExamQuestionResponse> response =
                examAttemptService.getExamQuestions(attemptId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/submit")
    public ResponseEntity<ExamSubmitResponse> submitExam(
            @Valid @RequestBody ExamSubmitRequest request
    ) {

        ExamSubmitResponse response =
                examAttemptService.submitExam(request);

        return ResponseEntity.ok(response);
    }
}