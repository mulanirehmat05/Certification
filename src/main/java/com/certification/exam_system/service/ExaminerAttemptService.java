package com.certification.exam_system.service;

import com.certification.exam_system.dto.ExaminerAttemptResponse;
import com.certification.exam_system.dto.ExaminerSessionSummaryResponse;
import com.certification.exam_system.entity.ExamAttempt;
import com.certification.exam_system.entity.ExamAttemptStatus;
import com.certification.exam_system.entity.ExamSession;
import com.certification.exam_system.entity.ExaminerAssignment;
import com.certification.exam_system.entity.Role;
import com.certification.exam_system.entity.User;
import com.certification.exam_system.repository.ExamAssignmentRepository;
import com.certification.exam_system.repository.ExamAttemptRepository;
import com.certification.exam_system.repository.ExaminerAssignmentRepository;
import com.certification.exam_system.repository.ExamSessionRepository;
import com.certification.exam_system.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ExaminerAttemptService {

    private final ExamAttemptRepository examAttemptRepository;
    private final ExaminerAssignmentRepository examinerAssignmentRepository;
    private final UserRepository userRepository;
    private final ExamSessionRepository examSessionRepository;
    private final ExamAssignmentRepository examAssignmentRepository;

    public ExaminerAttemptService(
            ExamAttemptRepository examAttemptRepository,
            ExaminerAssignmentRepository examinerAssignmentRepository,
            UserRepository userRepository,
            ExamSessionRepository examSessionRepository,
            ExamAssignmentRepository examAssignmentRepository
    ) {
        this.examAttemptRepository = examAttemptRepository;
        this.examinerAssignmentRepository = examinerAssignmentRepository;
        this.userRepository = userRepository;
        this.examSessionRepository = examSessionRepository;
        this.examAssignmentRepository = examAssignmentRepository;
    }

    @Transactional(readOnly = true)
    public List<ExaminerAttemptResponse> getSessionAttempts(
            Long examSessionId
    ) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        User examiner =
                userRepository.findByUsername(username)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Examiner not found"
                                )
                        );

        if (examiner.getRole() != Role.EXAMINER) {
            throw new IllegalArgumentException(
                    "Logged-in user is not an examiner"
            );
        }

        examinerAssignmentRepository
                .findByExaminerIdAndExamSessionId(
                        examiner.getId(),
                        examSessionId
                )
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "You are not assigned to this exam session"
                        )
                );

        List<ExamAttempt> attempts =
                examAttemptRepository
                        .findByExamSessionIdOrderByStartedAtAsc(
                                examSessionId
                        );

        return attempts.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ExaminerSessionSummaryResponse getSessionSummary(
            Long examSessionId
    ) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        User examiner =
                userRepository.findByUsername(username)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Examiner not found"
                                )
                        );

        if (examiner.getRole() != Role.EXAMINER) {
            throw new IllegalArgumentException(
                    "Logged-in user is not an examiner"
            );
        }

        ExamSession examSession =
                examSessionRepository.findById(examSessionId)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Exam session not found"
                                )
                        );

        examinerAssignmentRepository
                .findByExaminerIdAndExamSessionId(
                        examiner.getId(),
                        examSessionId
                )
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "You are not assigned to this exam session"
                        )
                );

        long totalCandidates =
                examAssignmentRepository
                        .countByExamSessionId(examSessionId);

        long inProgress =
                examAttemptRepository
                        .countByExamSessionIdAndStatus(
                                examSessionId,
                                ExamAttemptStatus.IN_PROGRESS
                        );

        long submitted =
                examAttemptRepository
                        .countByExamSessionIdAndStatus(
                                examSessionId,
                                ExamAttemptStatus.SUBMITTED
                        );

        long evaluated =
                examAttemptRepository
                        .countByExamSessionIdAndStatus(
                                examSessionId,
                                ExamAttemptStatus.EVALUATED
                        );

        long attemptsStarted =
                inProgress + submitted + evaluated;

        return new ExaminerSessionSummaryResponse(
                examSession.getId(),
                examSession.getSessionCode(),
                (int) totalCandidates,
                (int) attemptsStarted,
                (int) inProgress,
                (int) submitted,
                (int) evaluated
        );
    }

    private ExaminerAttemptResponse mapToResponse(
            ExamAttempt attempt
    ) {

        String candidateName =
                attempt.getCandidate().getFirstName()
                        + " "
                        + attempt.getCandidate().getLastName();

        return new ExaminerAttemptResponse(
                attempt.getId(),
                attempt.getCandidate().getId(),
                attempt.getCandidate().getCandidateCode(),
                candidateName,
                attempt.getExamSession().getId(),
                attempt.getExamSession().getSessionCode(),
                attempt.getStatus(),
                attempt.getStartedAt(),
                attempt.getSubmittedAt()
        );
    }
}