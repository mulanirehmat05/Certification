package com.certification.exam_system.repository;

import com.certification.exam_system.entity.Certification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CertificationRepository
        extends JpaRepository<Certification, Long> {

    Optional<Certification> findByCodeIgnoreCase(
            String code
    );

    boolean existsByCodeIgnoreCase(String code);

    List<Certification> findByCategoryId(
            Long categoryId
    );

    List<Certification> findByActiveTrue();
}