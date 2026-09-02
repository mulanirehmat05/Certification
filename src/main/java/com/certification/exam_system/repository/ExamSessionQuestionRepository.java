package com.certification.exam_system.repository;

import com.certification.exam_system.entity.ExamSessionQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExamSessionQuestionRepository
        extends JpaRepository<ExamSessionQuestion, Long> {

    boolean existsByExamSessionIdAndQuestionId(
            Long examSessionId,
            Long questionId
    );

    Optional<ExamSessionQuestion>
    findByExamSessionIdAndQuestionId(
            Long examSessionId,
            Long questionId
    );

    List<ExamSessionQuestion>
    findByExamSessionIdOrderByQuestionOrderAsc(
            Long examSessionId
    );

    List<ExamSessionQuestion>
    findByQuestionId(
            Long questionId
    );

    long countByExamSessionId(
            Long examSessionId
    );
}