package com.certification.exam_system.repository;

import com.certification.exam_system.entity.CandidateDocument;
import com.certification.exam_system.entity.DocumentVerificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CandidateDocumentRepository
        extends JpaRepository<CandidateDocument, Long> {

    List<CandidateDocument> findByCandidateId(Long candidateId);

    List<CandidateDocument> findByCandidateIdAndVerificationStatus(
            Long candidateId,
            DocumentVerificationStatus verificationStatus
    );
}