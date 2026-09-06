package com.certification.exam_system.controller;

import com.certification.exam_system.dto.exam.ExamAnswerRequest;
import com.certification.exam_system.dto.exam.ExamAnswerResponse;
import com.certification.exam_system.service.ExamAnswerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/exam-answers")
public class ExamAnswerController {

    private final ExamAnswerService examAnswerService;

    public ExamAnswerController(
            ExamAnswerService examAnswerService
    ) {
        this.examAnswerService = examAnswerService;
    }

    @PostMapping
    public ResponseEntity<ExamAnswerResponse> saveAnswer(
            @Valid @RequestBody ExamAnswerRequest request
    ) {

        ExamAnswerResponse response =
                examAnswerService.saveAnswer(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}