package com.certification.exam_system.dto.candidate;

import com.certification.exam_system.entity.VerificationStatus;
import jakarta.validation.constraints.NotNull;

public class CandidateVerificationRequest {

    @NotNull(message = "Verification status is required")
    private VerificationStatus verificationStatus;

    public CandidateVerificationRequest() {
    }

    public VerificationStatus getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(
            VerificationStatus verificationStatus
    ) {
        this.verificationStatus = verificationStatus;
    }
}