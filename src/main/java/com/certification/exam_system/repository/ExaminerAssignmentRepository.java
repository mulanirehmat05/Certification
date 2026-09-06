package com.certification.exam_system.repository;

import com.certification.exam_system.entity.ExaminerAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExaminerAssignmentRepository extends JpaRepository<ExaminerAssignment, Long> {

    boolean existsByExaminerIdAndExamSessionId(Long examinerId, Long examSessionId);

    Optional<ExaminerAssignment> findByExaminerIdAndExamSessionId(
            Long examinerId,
            Long examSessionId
    );

    List<ExaminerAssignment> findByExaminerId(Long examinerId);

    List<ExaminerAssignment> findByExamSessionId(Long examSessionId);

    void deleteByExaminerIdAndExamSessionId(
            Long examinerId,
            Long examSessionId
    );
}