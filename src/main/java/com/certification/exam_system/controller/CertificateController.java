package com.certification.exam_system.controller;

import com.certification.exam_system.dto.exam.CertificateResponse;
import com.certification.exam_system.dto.exam.CertificateVerificationResponse;
import com.certification.exam_system.service.CertificatePdfService;
import com.certification.exam_system.service.CertificateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/api/certificates")
public class CertificateController {

    private final CertificateService certificateService;
    private final CertificatePdfService certificatePdfService;



    public CertificateController(
            CertificateService certificateService,
            CertificatePdfService certificatePdfService
    ) {
        this.certificateService = certificateService;
        this.certificatePdfService = certificatePdfService;
    }

    @PostMapping("/generate/{resultId}")
    public ResponseEntity<CertificateResponse> generateCertificate(
            @PathVariable Long resultId
    ) {
        CertificateResponse response =
                certificateService.generateCertificate(resultId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{certificateId}")
    public ResponseEntity<CertificateResponse> getCertificate(
            @PathVariable Long certificateId
    ) {
        CertificateResponse response =
                certificateService.getCertificateById(certificateId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/verify/{certificateNumber}")
    public ResponseEntity<CertificateVerificationResponse> verifyCertificate(
            @PathVariable String certificateNumber
    ) {
        CertificateVerificationResponse response =
                certificateService.verifyCertificate(certificateNumber);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{certificateId}/pdf")
    public ResponseEntity<byte[]> downloadCertificatePdf(
            @PathVariable Long certificateId
    ) {

        byte[] pdf =
                certificatePdfService.generateCertificatePdf(certificateId);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=certificate-" + certificateId + ".pdf"
                )
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}