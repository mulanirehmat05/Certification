package com.certification.exam_system.repository;

import com.certification.exam_system.entity.CertificationCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CertificationCategoryRepository
        extends JpaRepository<CertificationCategory, Long> {

    Optional<CertificationCategory> findByNameIgnoreCase(
            String name
    );

    boolean existsByNameIgnoreCase(String name);
}