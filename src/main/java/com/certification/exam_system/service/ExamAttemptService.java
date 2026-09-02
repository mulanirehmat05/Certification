package com.certification.exam_system.service;

import com.certification.exam_system.dto.exam.CandidateExamQuestionResponse;
import com.certification.exam_system.dto.exam.ExamAttemptRequest;
import com.certification.exam_system.dto.exam.ExamAttemptResponse;
import com.certification.exam_system.dto.exam.QuestionOptionResponse;
import com.certification.exam_system.entity.Candidate;
import com.certification.exam_system.entity.ExamAttempt;
import com.certification.exam_system.entity.ExamAttemptStatus;
import com.certification.exam_system.entity.ExamSession;
import com.certification.exam_system.entity.ExamSessionQuestion;
import com.certification.exam_system.entity.ExamSessionStatus;
import com.certification.exam_system.entity.Question;
import com.certification.exam_system.entity.User;
import com.certification.exam_system.repository.CandidateRepository;
import com.certification.exam_system.repository.ExamAssignmentRepository;
import com.certification.exam_system.repository.ExamAttemptRepository;
import com.certification.exam_system.repository.ExamSessionQuestionRepository;
import com.certification.exam_system.repository.ExamSessionRepository;
import com.certification.exam_system.repository.QuestionOptionRepository;
import com.certification.exam_system.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExamAttemptService {

    private final ExamAttemptRepository examAttemptRepository;
    private final ExamAssignmentRepository examAssignmentRepository;
    private final ExamSessionRepository examSessionRepository;
    private final CandidateRepository candidateRepository;
    private final UserRepository userRepository;
    private final ExamSessionQuestionRepository examSessionQuestionRepository;
    private final QuestionOptionRepository questionOptionRepository;

    public ExamAttemptService(
            ExamAttemptRepository examAttemptRepository,
            ExamAssignmentRepository examAssignmentRepository,
            ExamSessionRepository examSessionRepository,
            CandidateRepository candidateRepository,
            UserRepository userRepository,
            ExamSessionQuestionRepository examSessionQuestionRepository,
            QuestionOptionRepository questionOptionRepository
    ) {
        this.examAttemptRepository = examAttemptRepository;
        this.examAssignmentRepository = examAssignmentRepository;
        this.examSessionRepository = examSessionRepository;
        this.candidateRepository = candidateRepository;
        this.userRepository = userRepository;
        this.examSessionQuestionRepository = examSessionQuestionRepository;
        this.questionOptionRepository = questionOptionRepository;
    }

    @Transactional
    public ExamAttemptResponse startExam(
            ExamAttemptRequest request
    ) {

        Candidate candidate = getAuthenticatedCandidate();

        ExamSession examSession =
                examSessionRepository
                        .findById(request.getExamSessionId())
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Exam session not found"
                                )
                        );

        if (examSession.getStatus()
                != ExamSessionStatus.SCHEDULED) {

            throw new IllegalArgumentException(
                    "Exam can only be started when the session is scheduled"
            );
        }

        boolean assigned =
                examAssignmentRepository
                        .existsByCandidateIdAndExamSessionId(
                                candidate.getId(),
                                examSession.getId()
                        );

        if (!assigned) {

            throw new IllegalArgumentException(
                    "Candidate is not assigned to this exam session"
            );
        }

        if (examAttemptRepository
                .existsByCandidateIdAndExamSessionId(
                        candidate.getId(),
                        examSession.getId()
                )) {

            throw new IllegalArgumentException(
                    "Candidate already has an attempt for this exam session"
            );
        }

        ExamAttempt attempt =
                new ExamAttempt();

        attempt.setCandidate(candidate);
        attempt.setExamSession(examSession);
        attempt.setStatus(
                ExamAttemptStatus.IN_PROGRESS
        );
        attempt.setStartedAt(
                LocalDateTime.now()
        );

        ExamAttempt savedAttempt =
                examAttemptRepository.save(attempt);

        return mapToResponse(savedAttempt);
    }

    @Transactional(readOnly = true)
    public List<CandidateExamQuestionResponse> getExamQuestions(
            Long attemptId
    ) {

        Candidate candidate = getAuthenticatedCandidate();

        ExamAttempt examAttempt =
                examAttemptRepository
                        .findById(attemptId)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Exam attempt not found"
                                )
                        );

        if (!examAttempt.getCandidate().getId()
                .equals(candidate.getId())) {

            throw new IllegalArgumentException(
                    "You are not allowed to access this exam attempt"
            );
        }

        if (examAttempt.getStatus()
                != ExamAttemptStatus.IN_PROGRESS) {

            throw new IllegalArgumentException(
                    "Exam attempt is not in progress"
            );
        }

        Long examSessionId =
                examAttempt.getExamSession().getId();

        List<ExamSessionQuestion> sessionQuestions =
                examSessionQuestionRepository
                        .findByExamSessionIdOrderByQuestionOrderAsc(
                                examSessionId
                        );

        return sessionQuestions.stream()
                .map(sessionQuestion -> {

                    Question question =
                            sessionQuestion.getQuestion();

                    List<QuestionOptionResponse> options =
                            questionOptionRepository
                                    .findByQuestionId(
                                            question.getId()
                                    )
                                    .stream()
                                    .map(option ->
                                            new QuestionOptionResponse(
                                                    option.getId(),
                                                    option.getOptionLabel(),
                                                    option.getOptionText()
                                            )
                                    )
                                    .toList();

                    return new CandidateExamQuestionResponse(
                            question.getId(),
                            question.getQuestionText(),
                            question.getQuestionType(),
                            question.getDifficulty(),
                            question.getMarks(),
                            sessionQuestion.getQuestionOrder(),
                            options
                    );
                })
                .toList();
    }

    private Candidate getAuthenticatedCandidate() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()) {

            throw new IllegalArgumentException(
                    "Authenticated user not found"
            );
        }

        String username =
                authentication.getName();

        User user =
                userRepository
                        .findByUsername(username)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "User not found"
                                )
                        );

        return candidateRepository
                .findByUserId(user.getId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Candidate profile not found"
                        )
                );
    }

    private ExamAttemptResponse mapToResponse(
            ExamAttempt attempt
    ) {

        Candidate candidate =
                attempt.getCandidate();

        ExamSession examSession =
                attempt.getExamSession();

        return new ExamAttemptResponse(
                attempt.getId(),
                candidate.getId(),
                candidate.getCandidateCode(),
                candidate.getFirstName()
                        + " "
                        + candidate.getLastName(),
                examSession.getId(),
                examSession.getSessionCode(),
                examSession.getCertification().getCode(),
                examSession.getCertification().getName(),
                attempt.getStatus(),
                attempt.getStartedAt(),
                attempt.getSubmittedAt()
        );
    }
}