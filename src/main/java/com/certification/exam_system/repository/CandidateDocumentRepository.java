package com.certification.exam_system.repository;

import com.certification.exam_system.entity.CandidateDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CandidateDocumentRepository
        extends JpaRepository<CandidateDocument, Long> {

    List<CandidateDocument> findByCandidateId(
            Long candidateId
    );

    Optional<CandidateDocument> findByIdAndCandidateId(
            Long documentId,
            Long candidateId
    );
}