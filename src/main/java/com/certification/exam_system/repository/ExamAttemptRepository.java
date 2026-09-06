package com.certification.exam_system.repository;

import com.certification.exam_system.entity.ExamAttempt;
import com.certification.exam_system.entity.ExamAttemptStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExamAttemptRepository
        extends JpaRepository<ExamAttempt, Long> {

    Optional<ExamAttempt>
    findByCandidateIdAndExamSessionId(
            Long candidateId,
            Long examSessionId
    );

    boolean existsByCandidateIdAndExamSessionId(
            Long candidateId,
            Long examSessionId
    );

    List<ExamAttempt> findByCandidateId(
            Long candidateId
    );

    List<ExamAttempt> findByExamSessionIdOrderByStartedAtAsc(
            Long examSessionId
    );

    List<ExamAttempt> findByExamSessionId(
            Long examSessionId
    );

    List<ExamAttempt> findByStatus(
            ExamAttemptStatus status
    );

    long countByExamSessionIdAndStatus(
            Long examSessionId,
            ExamAttemptStatus status
    );
}