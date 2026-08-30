package com.certification.exam_system.controller;

import com.certification.exam_system.dto.exam.ExamAssignmentRequest;
import com.certification.exam_system.dto.exam.ExamAssignmentResponse;
import com.certification.exam_system.service.ExamAssignmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exam-assignments")
public class ExamAssignmentController {

    private final ExamAssignmentService examAssignmentService;

    public ExamAssignmentController(
            ExamAssignmentService examAssignmentService
    ) {
        this.examAssignmentService = examAssignmentService;
    }

    @PostMapping
    public ResponseEntity<ExamAssignmentResponse> createAssignment(
            @Valid @RequestBody ExamAssignmentRequest request
    ) {

        ExamAssignmentResponse response =
                examAssignmentService.createAssignment(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<ExamAssignmentResponse>>
    getAllAssignments() {

        return ResponseEntity.ok(
                examAssignmentService.getAllAssignments()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExamAssignmentResponse>
    getAssignmentById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                examAssignmentService.getAssignmentById(id)
        );
    }

    @GetMapping("/candidate/{candidateId}")
    public ResponseEntity<List<ExamAssignmentResponse>>
    getAssignmentsByCandidate(
            @PathVariable Long candidateId
    ) {

        return ResponseEntity.ok(
                examAssignmentService
                        .getAssignmentsByCandidate(candidateId)
        );
    }

    @GetMapping("/exam-session/{examSessionId}")
    public ResponseEntity<List<ExamAssignmentResponse>>
    getAssignmentsByExamSession(
            @PathVariable Long examSessionId
    ) {

        return ResponseEntity.ok(
                examAssignmentService
                        .getAssignmentsByExamSession(examSessionId)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssignment(
            @PathVariable Long id
    ) {

        examAssignmentService.deleteAssignment(id);

        return ResponseEntity.noContent().build();
    }
}