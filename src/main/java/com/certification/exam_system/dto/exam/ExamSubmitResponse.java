package com.certification.exam_system.dto.exam;

import com.certification.exam_system.entity.ExamAttemptStatus;

import java.time.LocalDateTime;

public class ExamSubmitResponse {

    private Long attemptId;
    private ExamAttemptStatus status;
    private LocalDateTime submittedAt;
    private String message;

    public ExamSubmitResponse() {
    }

    public ExamSubmitResponse(
            Long attemptId,
            ExamAttemptStatus status,
            LocalDateTime submittedAt,
            String message
    ) {
        this.attemptId = attemptId;
        this.status = status;
        this.submittedAt = submittedAt;
        this.message = message;
    }

    public Long getAttemptId() {
        return attemptId;
    }

    public ExamAttemptStatus getStatus() {
        return status;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public String getMessage() {
        return message;
    }
}