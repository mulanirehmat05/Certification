package com.certification.exam_system.dto.document;

import com.certification.exam_system.entity.DocumentType;
import com.certification.exam_system.entity.DocumentVerificationStatus;

import java.time.LocalDateTime;

public class DocumentResponse {

    private Long id;
    private Long candidateId;
    private DocumentType documentType;
    private String documentName;
    private DocumentVerificationStatus verificationStatus;
    private LocalDateTime uploadedAt;
    private LocalDateTime verifiedAt;

    public DocumentResponse() {
    }

    public DocumentResponse(
            Long id,
            Long candidateId,
            DocumentType documentType,
            String documentName,
            DocumentVerificationStatus verificationStatus,
            LocalDateTime uploadedAt,
            LocalDateTime verifiedAt
    ) {
        this.id = id;
        this.candidateId = candidateId;
        this.documentType = documentType;
        this.documentName = documentName;
        this.verificationStatus = verificationStatus;
        this.uploadedAt = uploadedAt;
        this.verifiedAt = verifiedAt;
    }

    public Long getId() {
        return id;
    }

    public Long getCandidateId() {
        return candidateId;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public String getDocumentName() {
        return documentName;
    }

    public DocumentVerificationStatus getVerificationStatus() {
        return verificationStatus;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public LocalDateTime getVerifiedAt() {
        return verifiedAt;
    }
}