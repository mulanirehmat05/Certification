package com.certification.exam_system.repository;

import com.certification.exam_system.entity.ExamResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExamResultRepository
        extends JpaRepository<ExamResult, Long> {

    Optional<ExamResult> findByExamAttemptId(Long examAttemptId);

    boolean existsByExamAttemptId(Long examAttemptId);
}