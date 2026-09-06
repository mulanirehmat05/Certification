package com.certification.exam_system.controller;

import com.certification.exam_system.dto.ExaminerAnswerResponse;
import com.certification.exam_system.service.ExaminerAnswerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/examiner-answers")
public class ExaminerAnswerController {

    private final ExaminerAnswerService examinerAnswerService;

    public ExaminerAnswerController(
            ExaminerAnswerService examinerAnswerService
    ) {
        this.examinerAnswerService = examinerAnswerService;
    }

    @GetMapping("/attempt/{attemptId}")
    public ResponseEntity<List<ExaminerAnswerResponse>> getAttemptAnswers(
            @PathVariable Long attemptId
    ) {

        return ResponseEntity.ok(
                examinerAnswerService.getAttemptAnswers(attemptId)
        );
    }
}