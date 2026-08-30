package com.certification.exam_system.repository;

import com.certification.exam_system.entity.ExamAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExamAssignmentRepository
        extends JpaRepository<ExamAssignment, Long> {

    boolean existsByCandidateIdAndExamSessionId(
            Long candidateId,
            Long examSessionId
    );

    Optional<ExamAssignment> findByCandidateIdAndExamSessionId(
            Long candidateId,
            Long examSessionId
    );

    List<ExamAssignment> findByCandidateId(
            Long candidateId
    );

    List<ExamAssignment> findByExamSessionId(
            Long examSessionId
    );

    long countByExamSessionId(
            Long examSessionId
    );
}