package com.certification.exam_system.service;

import com.certification.exam_system.dto.exam.CertificateResponse;
import com.certification.exam_system.entity.Candidate;
import com.certification.exam_system.entity.Certificate;
import com.certification.exam_system.entity.ExamResult;
import com.certification.exam_system.entity.User;
import com.certification.exam_system.repository.CandidateRepository;
import com.certification.exam_system.repository.CertificateRepository;
import com.certification.exam_system.repository.ExamResultRepository;
import com.certification.exam_system.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.certification.exam_system.dto.exam.CertificateVerificationResponse;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CertificateService {

    private final CertificateRepository certificateRepository;
    private final ExamResultRepository examResultRepository;
    private final UserRepository userRepository;
    private final CandidateRepository candidateRepository;

    public CertificateService(
            CertificateRepository certificateRepository,
            ExamResultRepository examResultRepository,
            UserRepository userRepository,
            CandidateRepository candidateRepository
    ) {
        this.certificateRepository = certificateRepository;
        this.examResultRepository = examResultRepository;
        this.userRepository = userRepository;
        this.candidateRepository = candidateRepository;
    }

    @Transactional
    public CertificateResponse generateCertificate(Long resultId) {

        ExamResult examResult =
                examResultRepository.findById(resultId)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Exam result not found"
                                )
                        );

        if (!examResult.getPassed()) {
            throw new IllegalArgumentException(
                    "Certificate can only be generated for passed exams"
            );
        }

        if (certificateRepository.existsByExamResultId(resultId)) {
            throw new IllegalArgumentException(
                    "Certificate already generated for this result"
            );
        }

        Certificate certificate = new Certificate();

        certificate.setExamResult(examResult);

        certificate.setCertificateNumber(
                "CERT-" + UUID.randomUUID()
                        .toString()
                        .substring(0, 8)
                        .toUpperCase()
        );

        certificate.setIssuedAt(LocalDateTime.now());

        Certificate savedCertificate =
                certificateRepository.save(certificate);

        return new CertificateResponse(
                savedCertificate.getId(),
                examResult.getId(),
                examResult.getExamAttempt().getId(),
                savedCertificate.getCertificateNumber(),
                savedCertificate.getIssuedAt()
        );
    }

    @Transactional(readOnly = true)
    public CertificateResponse getCertificateById(Long certificateId) {

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
                        "You are not authorized to view this certificate"
                );
            }
        }

        return new CertificateResponse(
                certificate.getId(),
                certificate.getExamResult().getId(),
                certificate.getExamResult()
                        .getExamAttempt()
                        .getId(),
                certificate.getCertificateNumber(),
                certificate.getIssuedAt()
        );
    }

    @Transactional(readOnly = true)
    public CertificateVerificationResponse verifyCertificate(
            String certificateNumber
    ) {

        Certificate certificate =
                certificateRepository
                        .findByCertificateNumber(certificateNumber)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Certificate not found"
                                )
                        );

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

        return new CertificateVerificationResponse(
                true,
                certificate.getCertificateNumber(),
                candidateName,
                certificationName,
                examResult.getPercentage(),
                examResult.getPassed(),
                certificate.getIssuedAt()
        );
    }
}