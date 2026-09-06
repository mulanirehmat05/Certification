package com.certification.exam_system.dto.exam;

import jakarta.validation.constraints.NotNull;

public class ExamSubmitRequest {

    @NotNull(message = "Attempt ID is required")
    private Long attemptId;

    public ExamSubmitRequest() {
    }

    public Long getAttemptId() {
        return attemptId;
    }

    public void setAttemptId(Long attemptId) {
        this.attemptId = attemptId;
    }
}