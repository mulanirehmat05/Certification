package com.certification.exam_system.controller;

import com.certification.exam_system.dto.certification.CertificationRequest;
import com.certification.exam_system.dto.certification.CertificationResponse;
import com.certification.exam_system.service.CertificationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/certifications")
public class CertificationController {

    private final CertificationService certificationService;

    public CertificationController(CertificationService certificationService) {
        this.certificationService = certificationService;
    }

    @PostMapping
    public ResponseEntity<CertificationResponse> createCertification(
            @Valid @RequestBody CertificationRequest request
    ) {

        CertificationResponse response =
                certificationService.createCertification(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<CertificationResponse>> getAllCertifications() {

        return ResponseEntity.ok(
                certificationService.getAllCertifications()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CertificationResponse> getCertificationById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                certificationService.getCertificationById(id)
        );
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<CertificationResponse>> getByCategory(
            @PathVariable Long categoryId
    ) {

        return ResponseEntity.ok(
                certificationService.getCertificationsByCategory(categoryId)
        );
    }

    @GetMapping("/active")
    public ResponseEntity<List<CertificationResponse>> getActiveCertifications() {

        return ResponseEntity.ok(
                certificationService.getActiveCertifications()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<CertificationResponse> updateCertification(
            @PathVariable Long id,
            @Valid @RequestBody CertificationRequest request
    ) {

        return ResponseEntity.ok(
                certificationService.updateCertification(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCertification(
            @PathVariable Long id
    ) {

        certificationService.deleteCertification(id);

        return ResponseEntity.noContent().build();
    }
}