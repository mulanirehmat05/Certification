package com.certification.exam_system.repository;

import com.certification.exam_system.entity.ExamSession;
import com.certification.exam_system.entity.ExamSessionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ExamSessionRepository
        extends JpaRepository<ExamSession, Long> {

    Optional<ExamSession> findBySessionCodeIgnoreCase(
            String sessionCode
    );

    boolean existsBySessionCodeIgnoreCase(
            String sessionCode
    );

    List<ExamSession> findByCertificationId(
            Long certificationId
    );

    List<ExamSession> findByStatus(
            ExamSessionStatus status
    );

    List<ExamSession> findByExamDate(
            LocalDate examDate
    );
}