package com.certification.exam_system.dto.exam;

import java.time.LocalDateTime;

public class ExamAssignmentResponse {

    private Long id;

    private Long candidateId;
    private String candidateCode;
    private String candidateName;

    private Long examSessionId;
    private String sessionCode;

    private String certificationCode;
    private String certificationName;

    private LocalDateTime assignedAt;

    public ExamAssignmentResponse() {
    }

    public ExamAssignmentResponse(
            Long id,
            Long candidateId,
            String candidateCode,
            String candidateName,
            Long examSessionId,
            String sessionCode,
            String certificationCode,
            String certificationName,
            LocalDateTime assignedAt
    ) {
        this.id = id;
        this.candidateId = candidateId;
        this.candidateCode = candidateCode;
        this.candidateName = candidateName;
        this.examSessionId = examSessionId;
        this.sessionCode = sessionCode;
        this.certificationCode = certificationCode;
        this.certificationName = certificationName;
        this.assignedAt = assignedAt;
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

    public LocalDateTime getAssignedAt() {
        return assignedAt;
    }
}