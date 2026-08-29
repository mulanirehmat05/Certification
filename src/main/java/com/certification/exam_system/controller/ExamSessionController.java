package com.certification.exam_system.controller;

import com.certification.exam_system.dto.exam.ExamSessionRequest;
import com.certification.exam_system.dto.exam.ExamSessionResponse;
import com.certification.exam_system.entity.ExamSessionStatus;
import com.certification.exam_system.service.ExamSessionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.certification.exam_system.dto.exam.UpdateExamSessionStatusRequest;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/exam-sessions")
public class ExamSessionController {

    private final ExamSessionService examSessionService;

    public ExamSessionController(
            ExamSessionService examSessionService
    ) {
        this.examSessionService = examSessionService;
    }

    @PostMapping
    public ResponseEntity<ExamSessionResponse>
    createExamSession(
            @Valid @RequestBody ExamSessionRequest request
    ) {

        ExamSessionResponse response =
                examSessionService.createExamSession(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<ExamSessionResponse>>
    getAllExamSessions() {

        return ResponseEntity.ok(
                examSessionService.getAllExamSessions()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExamSessionResponse>
    getExamSessionById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                examSessionService.getExamSessionById(id)
        );
    }

    @GetMapping("/certification/{certificationId}")
    public ResponseEntity<List<ExamSessionResponse>>
    getSessionsByCertification(
            @PathVariable Long certificationId
    ) {

        return ResponseEntity.ok(
                examSessionService
                        .getSessionsByCertification(certificationId)
        );
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<ExamSessionResponse>>
    getSessionsByStatus(
            @PathVariable ExamSessionStatus status
    ) {

        return ResponseEntity.ok(
                examSessionService
                        .getSessionsByStatus(status)
        );
    }

    @GetMapping("/date/{examDate}")
    public ResponseEntity<List<ExamSessionResponse>>
    getSessionsByDate(
            @PathVariable LocalDate examDate
    ) {

        return ResponseEntity.ok(
                examSessionService
                        .getSessionsByDate(examDate)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExamSessionResponse>
    updateExamSession(
            @PathVariable Long id,
            @Valid @RequestBody ExamSessionRequest request
    ) {

        return ResponseEntity.ok(
                examSessionService
                        .updateExamSession(id, request)
        );
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ExamSessionResponse>
    updateExamSessionStatus(
            @PathVariable Long id,
            @Valid @RequestBody
            UpdateExamSessionStatusRequest request
    ) {

        return ResponseEntity.ok(
                examSessionService.updateExamSessionStatus(
                        id,
                        request
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExamSession(
            @PathVariable Long id
    ) {

        examSessionService.deleteExamSession(id);

        return ResponseEntity.noContent().build();
    }
}