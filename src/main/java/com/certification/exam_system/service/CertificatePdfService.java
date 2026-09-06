package com.certification.exam_system.service;

import com.certification.exam_system.entity.Candidate;
import com.certification.exam_system.entity.Certificate;
import com.certification.exam_system.entity.ExamResult;
import com.certification.exam_system.entity.User;
import com.certification.exam_system.repository.CandidateRepository;
import com.certification.exam_system.repository.CertificateRepository;
import com.certification.exam_system.repository.UserRepository;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;

@Service
public class CertificatePdfService {

    private final CertificateRepository certificateRepository;
    private final UserRepository userRepository;
    private final CandidateRepository candidateRepository;

    public CertificatePdfService(
            CertificateRepository certificateRepository,
            UserRepository userRepository,
            CandidateRepository candidateRepository
    ) {
        this.certificateRepository = certificateRepository;
        this.userRepository = userRepository;
        this.candidateRepository = candidateRepository;
    }

    @Transactional(readOnly = true)
    public byte[] generateCertificatePdf(Long certificateId) {

        Certificate certificate =
                certificateRepository.findById(certificateId)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Certificate not found"
                                )
                        );

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        boolean isAdmin =
                authentication.getAuthorities()
                        .stream()
                        .anyMatch(authority ->
                                authority.getAuthority()
                                        .equals("ROLE_ADMIN")
                        );

        if (!isAdmin) {

            String username = authentication.getName();

            User user =
                    userRepository.findByUsername(username)
                            .orElseThrow(() ->
                                    new IllegalArgumentException(
                                            "Authenticated user not found"
                                    )
                            );

            Candidate candidate =
                    candidateRepository.findByUserId(user.getId())
                            .orElseThrow(() ->
                                    new IllegalArgumentException(
                                            "Candidate profile not found"
                                    )
                            );

            Long certificateCandidateId =
                    certificate.getExamResult()
                            .getExamAttempt()
                            .getCandidate()
                            .getId();

            if (!candidate.getId().equals(certificateCandidateId)) {
                throw new IllegalArgumentException(
                        "You are not authorized to download this certificate"
                );
            }
        }

        ExamResult examResult =
                certificate.getExamResult();

        String candidateName =
                examResult.getExamAttempt()
                        .getCandidate()
                        .getFirstName()
                        + " "
                        + examResult.getExamAttempt()
                        .getCandidate()
                        .getLastName();

        String certificationName =
                examResult.getExamAttempt()
                        .getExamSession()
                        .getCertification()
                        .getName();

        ByteArrayOutputStream outputStream =
                new ByteArrayOutputStream();

        Document document = new Document();

        PdfWriter.getInstance(document, outputStream);

        document.open();

        Font titleFont =
                new Font(Font.HELVETICA, 24, Font.BOLD);

        Font normalFont =
                new Font(Font.HELVETICA, 14, Font.NORMAL);

        Paragraph title =
                new Paragraph(
                        "CERTIFICATE OF COMPLETION",
                        titleFont
                );

        title.setAlignment(Element.ALIGN_CENTER);

        document.add(title);

        document.add(new Paragraph(" "));

        Paragraph candidate =
                new Paragraph(
                        "This certificate is proudly presented to\n\n"
                                + candidateName,
                        normalFont
                );

        candidate.setAlignment(Element.ALIGN_CENTER);

        document.add(candidate);

        document.add(new Paragraph(" "));

        Paragraph certification =
                new Paragraph(
                        "for successfully completing the certification:\n\n"
                                + certificationName,
                        normalFont
                );

        certification.setAlignment(Element.ALIGN_CENTER);

        document.add(certification);

        document.add(new Paragraph(" "));

        Paragraph result =
                new Paragraph(
                        "Percentage: "
                                + examResult.getPercentage()
                                + "%\n"
                                + "Result: "
                                + (examResult.getPassed()
                                ? "PASSED"
                                : "FAILED")
                                + "\n"
                                + "Certificate Number: "
                                + certificate.getCertificateNumber(),
                        normalFont
                );

        result.setAlignment(Element.ALIGN_CENTER);

        document.add(result);

        document.add(new Paragraph(" "));

        Paragraph issuedAt =
                new Paragraph(
                        "Issued At: "
                                + certificate.getIssuedAt(),
                        normalFont
                );

        issuedAt.setAlignment(Element.ALIGN_CENTER);

        document.add(issuedAt);

        document.close();

        return outputStream.toByteArray();
    }
}