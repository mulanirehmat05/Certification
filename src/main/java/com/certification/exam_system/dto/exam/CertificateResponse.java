package com.certification.exam_system.dto.exam;

import java.time.LocalDateTime;

public class CertificateResponse {

    private Long certificateId;
    private Long resultId;
    private Long attemptId;
    private String certificateNumber;
    private LocalDateTime issuedAt;

    public CertificateResponse() {
    }

    public CertificateResponse(
            Long certificateId,
            Long resultId,
            Long attemptId,
            String certificateNumber,
            LocalDateTime issuedAt
    ) {
        this.certificateId = certificateId;
        this.resultId = resultId;
        this.attemptId = attemptId;
        this.certificateNumber = certificateNumber;
        this.issuedAt = issuedAt;
    }

    public Long getCertificateId() {
        return certificateId;
    }

    public Long getResultId() {
        return resultId;
    }

    public Long getAttemptId() {
        return attemptId;
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }
}