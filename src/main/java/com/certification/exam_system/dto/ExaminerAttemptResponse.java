package com.certification.exam_system.dto;

import com.certification.exam_system.entity.ExamAttemptStatus;

import java.time.LocalDateTime;

public class ExaminerAttemptResponse {

    private Long attemptId;

    private Long candidateId;
    private String candidateCode;
    private String candidateName;

    private Long examSessionId;
    private String sessionCode;

    private ExamAttemptStatus status;

    private LocalDateTime startedAt;
    private LocalDateTime submittedAt;

    public ExaminerAttemptResponse() {
    }

    public ExaminerAttemptResponse(
            Long attemptId,
            Long candidateId,
            String candidateCode,
            String candidateName,
            Long examSessionId,
            String sessionCode,
            ExamAttemptStatus status,
            LocalDateTime startedAt,
            LocalDateTime submittedAt
    ) {
        this.attemptId = attemptId;
        this.candidateId = candidateId;
        this.candidateCode = candidateCode;
        this.candidateName = candidateName;
        this.examSessionId = examSessionId;
        this.sessionCode = sessionCode;
        this.status = status;
        this.startedAt = startedAt;
        this.submittedAt = submittedAt;
    }

    public Long getAttemptId() {
        return attemptId;
    }

    public Long getCandidateId() {
        return candidateId;
    }

    public String getCandidateCode() {
        return candidateCode;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public Long getExamSessionId() {
        return examSessionId;
    }

    public String getSessionCode() {
        return sessionCode;
    }

    public ExamAttemptStatus getStatus() {
        return status;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }
}