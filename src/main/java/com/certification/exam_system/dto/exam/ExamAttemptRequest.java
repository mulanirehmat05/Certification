package com.certification.exam_system.dto.exam;

import jakarta.validation.constraints.NotNull;

public class ExamAttemptRequest {

    @NotNull(message = "Exam session ID is required")
    private Long examSessionId;

    public ExamAttemptRequest() {
    }

    public Long getExamSessionId() {
        return examSessionId;
    }

    public void setExamSessionId(Long examSessionId) {
        this.examSessionId = examSessionId;
    }
}