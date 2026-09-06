package com.certification.exam_system.service;

import com.certification.exam_system.dto.ExaminerAssignmentResponse;
import com.certification.exam_system.dto.ExaminerSessionResponse;
import com.certification.exam_system.entity.*;
import com.certification.exam_system.repository.ExaminerAssignmentRepository;
import com.certification.exam_system.repository.ExamSessionRepository;
import com.certification.exam_system.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@Service
public class ExaminerAssignmentService {

    private final ExaminerAssignmentRepository examinerAssignmentRepository;
    private final UserRepository userRepository;
    private final ExamSessionRepository examSessionRepository;

    public ExaminerAssignmentService(
            ExaminerAssignmentRepository examinerAssignmentRepository,
            UserRepository userRepository,
            ExamSessionRepository examSessionRepository
    ) {
        this.examinerAssignmentRepository = examinerAssignmentRepository;
        this.userRepository = userRepository;
        this.examSessionRepository = examSessionRepository;
    }

    @Transactional
    public ExaminerAssignmentResponse assignExaminer(
            Long examinerId,
            Long examSessionId
    ) {

        User examiner = userRepository.findById(examinerId)
                .orElseThrow(() -> new IllegalArgumentException("Examiner not found"));

        if (examiner.getRole() != Role.EXAMINER) {
            throw new IllegalArgumentException("Selected user is not an examiner");
        }

        if (!examiner.getActive()) {
            throw new IllegalArgumentException("Examiner is inactive");
        }

        ExamSession examSession = examSessionRepository.findById(examSessionId)
                .orElseThrow(() -> new IllegalArgumentException("Exam session not found"));

        if (examSession.getStatus() == ExamSessionStatus.ONGOING
                || examSession.getStatus() == ExamSessionStatus.COMPLETED
                || examSession.getStatus() == ExamSessionStatus.CANCELLED) {

            throw new IllegalArgumentException(
                    "Examiner cannot be assigned to this exam session in its current status"
            );
        }

        if (examinerAssignmentRepository
                .existsByExaminerIdAndExamSessionId(examinerId, examSessionId)) {

            throw new IllegalArgumentException(
                    "Examiner is already assigned to this exam session"
            );
        }

        ExaminerAssignment assignment = new ExaminerAssignment();

        assignment.setExaminer(examiner);
        assignment.setExamSession(examSession);

        ExaminerAssignment saved =
                examinerAssignmentRepository.save(assignment);

        return mapToResponse(saved);
    }

    private ExaminerAssignmentResponse mapToResponse(
            ExaminerAssignment assignment
    ) {

        User examiner = assignment.getExaminer();
        ExamSession examSession = assignment.getExamSession();

        return new ExaminerAssignmentResponse(
                assignment.getId(),
                examiner.getId(),
                examiner.getUsername(),
                examiner.getEmail(),
                examSession.getId(),
                examSession.getSessionCode(),
                assignment.getAssignedAt()
        );
    }

    @Transactional(readOnly = true)
    public List<ExaminerAssignmentResponse> getExaminersBySession(Long examSessionId) {

        List<ExaminerAssignment> assignments =
                examinerAssignmentRepository.findByExamSessionId(examSessionId);

        return assignments.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ExaminerSessionResponse> getMySessions() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        User examiner = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Examiner not found"));

        if (examiner.getRole() != Role.EXAMINER) {
            throw new IllegalArgumentException("Logged-in user is not an examiner");
        }

        List<ExaminerAssignment> assignments =
                examinerAssignmentRepository.findByExaminerId(examiner.getId());

        return assignments.stream()
                .map(assignment -> {
                    ExamSession session = assignment.getExamSession();

                    return new ExaminerSessionResponse(
                            session.getId(),
                            session.getSessionCode(),
                            session.getCertification().getId(),
                            session.getCertification().getCode(),
                            session.getCertification().getName(),
                            session.getExamDate(),
                            session.getStartTime(),
                            session.getEndTime(),
                            session.getExamMode(),
                            session.getStatus(),
                            session.getExamCenter(),
                            session.getExamCenterAddress()
                    );
                })
                .toList();
    }

    @Transactional
    public void removeExaminer(
            Long examinerId,
            Long examSessionId
    ) {

        ExamSession examSession = examSessionRepository.findById(examSessionId)
                .orElseThrow(() -> new IllegalArgumentException("Exam session not found"));

        if (examSession.getStatus() == ExamSessionStatus.ONGOING
                || examSession.getStatus() == ExamSessionStatus.COMPLETED
                || examSession.getStatus() == ExamSessionStatus.CANCELLED) {

            throw new IllegalArgumentException(
                    "Examiner cannot be removed from this exam session in its current status"
            );
        }

        if (!examinerAssignmentRepository
                .existsByExaminerIdAndExamSessionId(examinerId, examSessionId)) {

            throw new IllegalArgumentException(
                    "Examiner is not assigned to this exam session"
            );
        }

        examinerAssignmentRepository.deleteByExaminerIdAndExamSessionId(
                examinerId,
                examSessionId
        );
    }
}