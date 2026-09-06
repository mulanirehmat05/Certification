package com.certification.exam_system.service;

import com.certification.exam_system.dto.exam.ExamResultResponse;
import com.certification.exam_system.entity.Candidate;
import com.certification.exam_system.entity.ExamResult;
import com.certification.exam_system.entity.User;
import com.certification.exam_system.repository.CandidateRepository;
import com.certification.exam_system.repository.ExamResultRepository;
import com.certification.exam_system.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExamResultService {

    private final ExamResultRepository examResultRepository;
    private final UserRepository userRepository;
    private final CandidateRepository candidateRepository;

    public ExamResultService(
            ExamResultRepository examResultRepository,
            UserRepository userRepository,
            CandidateRepository candidateRepository
    ) {
        this.examResultRepository = examResultRepository;
        this.userRepository = userRepository;
        this.candidateRepository = candidateRepository;
    }

    @Transactional(readOnly = true)
    public ExamResultResponse getResultByAttemptId(Long attemptId) {

        ExamResult examResult =
                examResultRepository
                        .findByExamAttemptId(attemptId)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Exam result not found for this attempt"
                                )
                        );

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        User user =
                userRepository
                        .findByUsername(username)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Authenticated user not found"
                                )
                        );

        Candidate candidate =
                candidateRepository
                        .findByUserId(user.getId())
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Candidate profile not found"
                                )
                        );

        Long resultCandidateId =
                examResult.getExamAttempt().getCandidate().getId();

        boolean isAdmin =
                authentication.getAuthorities()
                        .stream()
                        .anyMatch(authority ->
                                authority.getAuthority().equals("ROLE_ADMIN")
                        );

        if (!isAdmin && !candidate.getId().equals(resultCandidateId)) {
            throw new IllegalArgumentException(
                    "You are not authorized to view this exam result"
            );
        }

        return new ExamResultResponse(
                examResult.getId(),
                examResult.getExamAttempt().getId(),
                examResult.getTotalQuestions(),
                examResult.getAnsweredQuestions(),
                examResult.getCorrectAnswers(),
                examResult.getWrongAnswers(),
                examResult.getTotalMarks(),
                examResult.getObtainedMarks(),
                examResult.getPercentage(),
                examResult.getPassed(),
                examResult.getEvaluatedAt()
        );
    }
}