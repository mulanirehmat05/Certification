package com.certification.exam_system.dto.exam;

import com.certification.exam_system.entity.ExamAttemptStatus;

import java.time.LocalDateTime;

public class ExamAttemptResponse {

    private Long id;

    private Long candidateId;
    private String candidateCode;
    private String candidateName;

    private Long examSessionId;
    private String sessionCode;

    private String certificationCode;
    private String certificationName;

    private ExamAttemptStatus status;

    private LocalDateTime startedAt;
    private LocalDateTime submittedAt;

    public ExamAttemptResponse() {
    }

    public ExamAttemptResponse(
            Long id,
            Long candidateId,
            String candidateCode,
            String candidateName,
            Long examSessionId,
            String sessionCode,
            String certificationCode,
            String certificationName,
            ExamAttemptStatus status,
            LocalDateTime startedAt,
            LocalDateTime submittedAt
    ) {
        this.id = id;
        this.candidateId = candidateId;
        this.candidateCode = candidateCode;
        this.candidateName = candidateName;
        this.examSessionId = examSessionId;
        this.sessionCode = sessionCode;
        this.certificationCode = certificationCode;
        this.certificationName = certificationName;
        this.status = status;
        this.startedAt = startedAt;
        this.submittedAt = submittedAt;
    }

    public Long getId() {
        return id;
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

    public String getCertificationCode() {
        return certificationCode;
    }

    public String getCertificationName() {
        return certificationName;
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