package com.certification.exam_system.dto;

import java.time.LocalDateTime;

public class ExaminerAssignmentResponse {

    private Long id;

    private Long examinerId;
    private String examinerUsername;
    private String examinerEmail;

    private Long examSessionId;
    private String sessionCode;

    private LocalDateTime assignedAt;

    public ExaminerAssignmentResponse() {
    }

    public ExaminerAssignmentResponse(
            Long id,
            Long examinerId,
            String examinerUsername,
            String examinerEmail,
            Long examSessionId,
            String sessionCode,
            LocalDateTime assignedAt
    ) {
        this.id = id;
        this.examinerId = examinerId;
        this.examinerUsername = examinerUsername;
        this.examinerEmail = examinerEmail;
        this.examSessionId = examSessionId;
        this.sessionCode = sessionCode;
        this.assignedAt = assignedAt;
    }

    public Long getId() {
        return id;
    }

    public Long getExaminerId() {
        return examinerId;
    }

    public String getExaminerUsername() {
        return examinerUsername;
    }

    public String getExaminerEmail() {
        return examinerEmail;
    }

    public Long getExamSessionId() {
        return examSessionId;
    }

    public String getSessionCode() {
        return sessionCode;
    }

    public LocalDateTime getAssignedAt() {
        return assignedAt;
    }
}