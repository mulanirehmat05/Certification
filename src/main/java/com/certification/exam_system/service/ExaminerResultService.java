package com.certification.exam_system.service;

import com.certification.exam_system.dto.ExaminerResultResponse;
import com.certification.exam_system.entity.ExamResult;
import com.certification.exam_system.entity.ExaminerAssignment;
import com.certification.exam_system.entity.Role;
import com.certification.exam_system.entity.User;
import com.certification.exam_system.repository.ExamResultRepository;
import com.certification.exam_system.repository.ExaminerAssignmentRepository;
import com.certification.exam_system.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExaminerResultService {

    private final ExamResultRepository examResultRepository;
    private final ExaminerAssignmentRepository examinerAssignmentRepository;
    private final UserRepository userRepository;

    public ExaminerResultService(
            ExamResultRepository examResultRepository,
            ExaminerAssignmentRepository examinerAssignmentRepository,
            UserRepository userRepository
    ) {
        this.examResultRepository = examResultRepository;
        this.examinerAssignmentRepository = examinerAssignmentRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public ExaminerResultResponse getAttemptResult(Long attemptId) {

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

        ExamResult examResult =
                examResultRepository.findByExamAttemptId(attemptId)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Exam result not found for this attempt"
                                )
                        );

        Long examSessionId =
                examResult.getExamAttempt()
                        .getExamSession()
                        .getId();

        ExaminerAssignment assignment =
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

        String candidateName =
                examResult.getExamAttempt()
                        .getCandidate()
                        .getFirstName()
                        + " "
                        + examResult.getExamAttempt()
                        .getCandidate()
                        .getLastName();

        return new ExaminerResultResponse(
                examResult.getId(),
                examResult.getExamAttempt().getId(),

                examResult.getExamAttempt()
                        .getCandidate()
                        .getId(),

                examResult.getExamAttempt()
                        .getCandidate()
                        .getCandidateCode(),

                candidateName,

                examSessionId,

                examResult.getExamAttempt()
                        .getExamSession()
                        .getSessionCode(),

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