package com.certification.exam_system.service;

import com.certification.exam_system.dto.document.DocumentResponse;
import com.certification.exam_system.entity.Candidate;
import com.certification.exam_system.entity.CandidateDocument;
import com.certification.exam_system.entity.DocumentType;
import com.certification.exam_system.entity.DocumentVerificationStatus;
import com.certification.exam_system.repository.CandidateDocumentRepository;
import com.certification.exam_system.repository.CandidateRepository;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class CandidateDocumentService {

    private final CandidateDocumentRepository
            candidateDocumentRepository;

    private final CandidateRepository candidateRepository;

    private final DocumentStorageService
            documentStorageService;

    public CandidateDocumentService(
            CandidateDocumentRepository candidateDocumentRepository,
            CandidateRepository candidateRepository,
            DocumentStorageService documentStorageService
    ) {

        this.candidateDocumentRepository =
                candidateDocumentRepository;

        this.candidateRepository =
                candidateRepository;

        this.documentStorageService =
                documentStorageService;
    }

    public DocumentResponse uploadDocument(
            Long candidateId,
            DocumentType documentType,
            MultipartFile file
    ) {

        Candidate candidate =
                candidateRepository.findById(candidateId)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Candidate not found"
                                )
                        );

        if (documentType == null) {

            throw new IllegalArgumentException(
                    "Document type is required"
            );
        }

        validateFile(file);

        String documentReference =
                documentStorageService.storeFile(file);

        CandidateDocument document =
                new CandidateDocument();

        document.setCandidate(candidate);
        document.setDocumentType(documentType);
        document.setDocumentName(
                file.getOriginalFilename()
        );
        document.setDocumentReference(
                documentReference
        );

        CandidateDocument savedDocument =
                candidateDocumentRepository.save(
                        document
                );

        return mapToResponse(savedDocument);
    }

    public List<DocumentResponse> getCandidateDocuments(
            Long candidateId
    ) {

        if (!candidateRepository.existsById(candidateId)) {

            throw new IllegalArgumentException(
                    "Candidate not found"
            );
        }

        return candidateDocumentRepository
                .findByCandidateId(candidateId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private void validateFile(
            MultipartFile file
    ) {

        if (file == null || file.isEmpty()) {

            throw new IllegalArgumentException(
                    "File is required"
            );
        }

        String contentType =
                file.getContentType();

        if (contentType == null) {

            throw new IllegalArgumentException(
                    "File type could not be determined"
            );
        }

        boolean allowedType =
                contentType.equals("application/pdf")
                        || contentType.equals("image/jpeg")
                        || contentType.equals("image/png");

        if (!allowedType) {

            throw new IllegalArgumentException(
                    "Only PDF, JPG and PNG files are allowed"
            );
        }

        if (file.getSize() > 5 * 1024 * 1024) {

            throw new IllegalArgumentException(
                    "File size must not exceed 5MB"
            );
        }
    }

    private DocumentResponse mapToResponse(
            CandidateDocument document
    ) {

        return new DocumentResponse(
                document.getId(),
                document.getCandidate().getId(),
                document.getDocumentType(),
                document.getDocumentName(),
                document.getVerificationStatus(),
                document.getUploadedAt(),
                document.getVerifiedAt()
        );
    }

    public Resource downloadDocument(
            Long candidateId,
            Long documentId
    ) {

        CandidateDocument document =
                candidateDocumentRepository
                        .findByIdAndCandidateId(
                                documentId,
                                candidateId
                        )
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Document not found"
                                )
                        );

        byte[] fileData =
                documentStorageService.loadFile(
                        document.getDocumentReference()
                );

        return new ByteArrayResource(fileData);
    }

    public DocumentResponse updateVerificationStatus(
            Long documentId,
            DocumentVerificationStatus status
    ) {

        CandidateDocument document =
                candidateDocumentRepository.findById(documentId)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Document not found"
                                )
                        );

        document.setVerificationStatus(status);

        if (status ==
                DocumentVerificationStatus.VERIFIED
                || status ==
                DocumentVerificationStatus.REJECTED) {

            document.setVerifiedAt(
                    java.time.LocalDateTime.now()
            );

        } else {

            document.setVerifiedAt(null);
        }

        CandidateDocument updatedDocument =
                candidateDocumentRepository.save(document);

        return mapToResponse(updatedDocument);
    }
}