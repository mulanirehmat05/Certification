package com.certification.exam_system.repository;

import com.certification.exam_system.entity.QuestionOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionOptionRepository
        extends JpaRepository<QuestionOption, Long> {

    List<QuestionOption> findByQuestionId(
            Long questionId
    );

    @Modifying
    @Query("""
            DELETE FROM QuestionOption qo
            WHERE qo.question.id = :questionId
            """)
    void deleteByQuestionId(
            @Param("questionId") Long questionId
    );

    long countByQuestionId(
            Long questionId
    );

    long countByQuestionIdAndCorrectTrue(
            Long questionId
    );
}

