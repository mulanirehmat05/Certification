package com.certification.exam_system.controller;

import com.certification.exam_system.dto.exam.QuestionRequest;
import com.certification.exam_system.dto.exam.QuestionResponse;
import com.certification.exam_system.service.QuestionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(
            QuestionService questionService
    ) {
        this.questionService = questionService;
    }

    @PostMapping
    public ResponseEntity<QuestionResponse> createQuestion(
            @Valid @RequestBody QuestionRequest request
    ) {

        QuestionResponse response =
                questionService.createQuestion(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<QuestionResponse>>
    getAllQuestions() {

        return ResponseEntity.ok(
                questionService.getAllQuestions()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionResponse>
    getQuestionById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                questionService.getQuestionById(id)
        );
    }

    @GetMapping("/certification/{certificationId}")
    public ResponseEntity<List<QuestionResponse>>
    getQuestionsByCertification(
            @PathVariable Long certificationId
    ) {

        return ResponseEntity.ok(
                questionService
                        .getQuestionsByCertification(
                                certificationId
                        )
        );
    }

    @GetMapping("/certification/{certificationId}/active")
    public ResponseEntity<List<QuestionResponse>>
    getActiveQuestionsByCertification(
            @PathVariable Long certificationId
    ) {

        return ResponseEntity.ok(
                questionService
                        .getActiveQuestionsByCertification(
                                certificationId
                        )
        );
    }

    @GetMapping("/certification/{certificationId}/difficulty/{difficulty}")
    public ResponseEntity<List<QuestionResponse>>
    getQuestionsByDifficulty(
            @PathVariable Long certificationId,
            @PathVariable String difficulty
    ) {

        return ResponseEntity.ok(
                questionService.getQuestionsByDifficulty(
                        certificationId,
                        difficulty
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuestionResponse>
    updateQuestion(
            @PathVariable Long id,
            @Valid @RequestBody QuestionRequest request
    ) {

        return ResponseEntity.ok(
                questionService.updateQuestion(
                        id,
                        request
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(
            @PathVariable Long id
    ) {

        questionService.deleteQuestion(id);

        return ResponseEntity.noContent().build();
    }
}