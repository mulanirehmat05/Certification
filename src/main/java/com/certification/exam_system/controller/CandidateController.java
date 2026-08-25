package com.certification.exam_system.controller;

import com.certification.exam_system.dto.candidate.CandidateCreateRequest;
import com.certification.exam_system.dto.candidate.CandidateResponse;
import com.certification.exam_system.dto.candidate.CandidateUpdateRequest;
import com.certification.exam_system.dto.candidate.CandidateVerificationRequest;
import com.certification.exam_system.entity.VerificationStatus;
import com.certification.exam_system.service.CandidateService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/candidates")
public class CandidateController {

    private final CandidateService candidateService;

    public CandidateController(
            CandidateService candidateService
    ) {
        this.candidateService = candidateService;
    }

    @PostMapping
    public ResponseEntity<CandidateResponse> createCandidate(
            @Valid @RequestBody CandidateCreateRequest request
    ) {

        CandidateResponse response =
                candidateService.createCandidate(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CandidateResponse> getCandidate(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                candidateService.getCandidate(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<CandidateResponse> updateCandidate(
            @PathVariable Long id,
            @Valid @RequestBody CandidateUpdateRequest request
    ) {

        CandidateResponse response =
                candidateService.updateCandidate(id, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCandidate(
            @PathVariable Long id
    ) {

        candidateService.deleteCandidate(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<CandidateResponse>> searchCandidates(
            @RequestParam(required = false) String search,
            @RequestParam(required = false)
            VerificationStatus verificationStatus
    ) {

        return ResponseEntity.ok(
                candidateService.searchCandidates(
                        search,
                        verificationStatus
                )
        );
    }

    @PatchMapping("/{id}/verification")
    public ResponseEntity<CandidateResponse> updateVerificationStatus(
            @PathVariable Long id,
            @Valid @RequestBody
            CandidateVerificationRequest request
    ) {

        CandidateResponse response =
                candidateService.updateVerificationStatus(
                        id,
                        request.getVerificationStatus()
                );

        return ResponseEntity.ok(response);
    }
}