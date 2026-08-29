package com.certification.exam_system.dto.exam;

import com.certification.exam_system.entity.ExamSessionStatus;
import jakarta.validation.constraints.NotNull;

public class UpdateExamSessionStatusRequest {

    @NotNull(message = "Exam session status is required")
    private ExamSessionStatus status;

    public UpdateExamSessionStatusRequest() {
    }

    public ExamSessionStatus getStatus() {
        return status;
    }

    public void setStatus(ExamSessionStatus status) {
        this.status = status;
    }
}