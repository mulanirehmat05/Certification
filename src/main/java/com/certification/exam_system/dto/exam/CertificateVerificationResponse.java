package com.certification.exam_system.dto.exam;

import java.time.LocalDateTime;

public class CertificateVerificationResponse {

    private boolean valid;
    private String certificateNumber;
    private String candidateName;
    private String certificationName;
    private Double percentage;
    private Boolean passed;
    private LocalDateTime issuedAt;

    public CertificateVerificationResponse() {
    }

    public CertificateVerificationResponse(
            boolean valid,
            String certificateNumber,
            String candidateName,
            String certificationName,
            Double percentage,
            Boolean passed,
            LocalDateTime issuedAt
    ) {
        this.valid = valid;
        this.certificateNumber = certificateNumber;
        this.candidateName = candidateName;
        this.certificationName = certificationName;
        this.percentage = percentage;
        this.passed = passed;
        this.issuedAt = issuedAt;
    }

    public boolean isValid() {
        return valid;
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public String getCertificationName() {
        return certificationName;
    }

    public Double getPercentage() {
        return percentage;
    }

    public Boolean getPassed() {
        return passed;
    }

    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }
}