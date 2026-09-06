package com.certification.exam_system.repository;

import com.certification.exam_system.entity.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CertificateRepository
        extends JpaRepository<Certificate, Long> {

    Optional<Certificate> findByExamResultId(Long examResultId);

    Optional<Certificate> findByCertificateNumber(String certificateNumber);

    boolean existsByExamResultId(Long examResultId);

    boolean existsByCertificateNumber(String certificateNumber);
}