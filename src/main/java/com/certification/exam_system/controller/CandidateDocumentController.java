package com.certification.exam_system.controller;

import com.certification.exam_system.dto.document.DocumentResponse;
import com.certification.exam_system.dto.document.DocumentVerificationRequest;
import com.certification.exam_system.entity.DocumentType;
import com.certification.exam_system.service.CandidateDocumentService;
import jakarta.validation.Valid;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/candidates/{candidateId}/documents")
public class CandidateDocumentController {

    private final CandidateDocumentService
            candidateDocumentService;

    public CandidateDocumentController(
            CandidateDocumentService candidateDocumentService
    ) {

        this.candidateDocumentService =
                candidateDocumentService;
    }

    @PostMapping
    public ResponseEntity<DocumentResponse> uploadDocument(
            @PathVariable Long candidateId,
            @RequestParam DocumentType documentType,
            @RequestParam MultipartFile file
    ) {

        DocumentResponse response =
                candidateDocumentService.uploadDocument(
                        candidateId,
                        documentType,
                        file
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<DocumentResponse>>
    getCandidateDocuments(
            @PathVariable Long candidateId
    ) {

        return ResponseEntity.ok(
                candidateDocumentService
                        .getCandidateDocuments(candidateId)
        );
    }

    @GetMapping("/{documentId}/download")
    public ResponseEntity<Resource> downloadDocument(
            @PathVariable Long candidateId,
            @PathVariable Long documentId
    ) {

        Resource resource =
                candidateDocumentService
                        .downloadDocument(
                                candidateId,
                                documentId
                        );

        return ResponseEntity.ok()
                .contentType(
                        MediaType.APPLICATION_OCTET_STREAM
                )
                .header(
                        "Content-Disposition",
                        "attachment; filename=\"" +
                                resource.getFilename() +
                                "\""
                )
                .body(resource);
    }

    @PatchMapping("/{documentId}/verification")
    public ResponseEntity<DocumentResponse>
    updateVerificationStatus(
            @PathVariable Long candidateId,
            @PathVariable Long documentId,
            @Valid @RequestBody
            DocumentVerificationRequest request
    ) {

        DocumentResponse response =
                candidateDocumentService
                        .updateVerificationStatus(
                                documentId,
                                request.getVerificationStatus()
                        );

        return ResponseEntity.ok(response);
    }
}