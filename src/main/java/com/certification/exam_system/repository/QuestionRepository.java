package com.certification.exam_system.repository;

import com.certification.exam_system.entity.Question;
import com.certification.exam_system.entity.QuestionDifficulty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository
        extends JpaRepository<Question, Long> {

    List<Question> findByCertificationId(
            Long certificationId
    );

    List<Question> findByCertificationIdAndActiveTrue(
            Long certificationId
    );

    List<Question> findByDifficulty(
            QuestionDifficulty difficulty
    );

    List<Question> findByCertificationIdAndDifficulty(
            Long certificationId,
            QuestionDifficulty difficulty
    );

    List<Question> findByActiveTrue();

    long countByCertificationId(
            Long certificationId
    );

    long countByCertificationIdAndActiveTrue(
            Long certificationId
    );
}