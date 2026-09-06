package com.certification.exam_system.controller;

import com.certification.exam_system.dto.ExaminerAssignmentResponse;
import com.certification.exam_system.dto.ExaminerSessionResponse;
import com.certification.exam_system.service.ExaminerAssignmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/examiner-assignments")
public class ExaminerAssignmentController {

    private final ExaminerAssignmentService examinerAssignmentService;

    public ExaminerAssignmentController(
            ExaminerAssignmentService examinerAssignmentService
    ) {
        this.examinerAssignmentService = examinerAssignmentService;
    }

    @PostMapping
    public ResponseEntity<ExaminerAssignmentResponse> assignExaminer(
            @RequestParam Long examinerId,
            @RequestParam Long examSessionId
    ) {
        return ResponseEntity.ok(
                examinerAssignmentService.assignExaminer(
                        examinerId,
                        examSessionId
                )
        );
    }

    @GetMapping("/session/{examSessionId}")
    public ResponseEntity<List<ExaminerAssignmentResponse>> getExaminersBySession(
            @PathVariable Long examSessionId
    ) {
        return ResponseEntity.ok(
                examinerAssignmentService.getExaminersBySession(examSessionId)
        );
    }

    @GetMapping("/my-sessions")
    public ResponseEntity<List<ExaminerSessionResponse>> getMySessions() {

        return ResponseEntity.ok(
                examinerAssignmentService.getMySessions()
        );
    }

    @DeleteMapping
    public ResponseEntity<Void> removeExaminer(
            @RequestParam Long examinerId,
            @RequestParam Long examSessionId
    ) {

        examinerAssignmentService.removeExaminer(
                examinerId,
                examSessionId
        );

        return ResponseEntity.noContent().build();
    }
}