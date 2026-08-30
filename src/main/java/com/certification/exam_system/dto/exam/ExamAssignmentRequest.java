package com.certification.exam_system.dto.exam;

import jakarta.validation.constraints.NotNull;

public class ExamAssignmentRequest {

    @NotNull(message = "Candidate ID is required")
    private Long candidateId;

    @NotNull(message = "Exam session ID is required")
    private Long examSessionId;

    public ExamAssignmentRequest() {
    }

    public Long getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
    }

    public Long getExamSessionId() {
        return examSessionId;
    }

    public void setExamSessionId(Long examSessionId) {
        this.examSessionId = examSessionId;
    }
}