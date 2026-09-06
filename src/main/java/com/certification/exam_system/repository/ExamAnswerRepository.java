package com.certification.exam_system.repository;

import com.certification.exam_system.entity.ExamAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExamAnswerRepository
        extends JpaRepository<ExamAnswer, Long> {

    Optional<ExamAnswer> findByExamAttemptIdAndQuestionId(
            Long examAttemptId,
            Long questionId
    );

    boolean existsByExamAttemptIdAndQuestionId(
            Long examAttemptId,
            Long questionId
    );

    List<ExamAnswer> findByExamAttemptId(
            Long examAttemptId
    );

    List<ExamAnswer> findByExamAttemptIdOrderByQuestionIdAsc(
            Long examAttemptId
    );

    long countByExamAttemptId(
            Long examAttemptId
    );
}