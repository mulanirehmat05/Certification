package com.certification.exam_system.controller;

import com.certification.exam_system.dto.exam.ExamSessionQuestionRequest;
import com.certification.exam_system.dto.exam.ExamSessionQuestionResponse;
import com.certification.exam_system.service.ExamSessionQuestionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exam-session-questions")
public class ExamSessionQuestionController {

    private final ExamSessionQuestionService
            examSessionQuestionService;

    public ExamSessionQuestionController(
            ExamSessionQuestionService examSessionQuestionService
    ) {
        this.examSessionQuestionService =
                examSessionQuestionService;
    }

    @PostMapping
    public ResponseEntity<ExamSessionQuestionResponse>
    addQuestionToExamSession(
            @Valid @RequestBody
            ExamSessionQuestionRequest request
    ) {

        ExamSessionQuestionResponse response =
                examSessionQuestionService
                        .addQuestionToExamSession(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/exam-session/{examSessionId}")
    public ResponseEntity<List<ExamSessionQuestionResponse>>
    getQuestionsByExamSession(
            @PathVariable Long examSessionId
    ) {

        return ResponseEntity.ok(
                examSessionQuestionService
                        .getQuestionsByExamSession(
                                examSessionId
                        )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExamSessionQuestionResponse>
    getExamSessionQuestionById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                examSessionQuestionService
                        .getExamSessionQuestionById(id)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>
    removeQuestionFromExamSession(
            @PathVariable Long id
    ) {

        examSessionQuestionService
                .removeQuestionFromExamSession(id);

        return ResponseEntity.noContent().build();
    }
}