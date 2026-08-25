package com.certification.exam_system.dto.document;

import com.certification.exam_system.entity.DocumentVerificationStatus;
import jakarta.validation.constraints.NotNull;

public class DocumentVerificationRequest {

    @NotNull(message = "Verification status is required")
    private DocumentVerificationStatus verificationStatus;

    public DocumentVerificationRequest() {
    }

    public DocumentVerificationStatus getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(
            DocumentVerificationStatus verificationStatus
    ) {
        this.verificationStatus = verificationStatus;
    }
}