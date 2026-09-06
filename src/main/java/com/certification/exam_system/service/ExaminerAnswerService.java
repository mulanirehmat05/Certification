package com.certification.exam_system.service;

import com.certification.exam_system.dto.ExaminerAnswerResponse;
import com.certification.exam_system.entity.ExamAnswer;
import com.certification.exam_system.entity.ExamAttempt;
import com.certification.exam_system.entity.ExaminerAssignment;
import com.certification.exam_system.entity.Role;
import com.certification.exam_system.entity.User;
import com.certification.exam_system.repository.ExamAnswerRepository;
import com.certification.exam_system.repository.ExamAttemptRepository;
import com.certification.exam_system.repository.ExaminerAssignmentRepository;
import com.certification.exam_system.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ExaminerAnswerService {

    private final ExamAnswerRepository examAnswerRepository;
    private final ExamAttemptRepository examAttemptRepository;
    private final ExaminerAssignmentRepository examinerAssignmentRepository;
    private final UserRepository userRepository;

    public ExaminerAnswerService(
            ExamAnswerRepository examAnswerRepository,
            ExamAttemptRepository examAttemptRepository,
            ExaminerAssignmentRepository examinerAssignmentRepository,
            UserRepository userRepository
    ) {
        this.examAnswerRepository = examAnswerRepository;
        this.examAttemptRepository = examAttemptRepository;
        this.examinerAssignmentRepository = examinerAssignmentRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<ExaminerAnswerResponse> getAttemptAnswers(Long attemptId) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        User examiner = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Examiner not found"
                ));

        if (examiner.getRole() != Role.EXAMINER) {
            throw new IllegalArgumentException(
                    "Logged-in user is not an examiner"
            );
        }

        ExamAttempt attempt = examAttemptRepository.findById(attemptId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Exam attempt not found"
                ));

        Long examSessionId = attempt.getExamSession().getId();

        ExaminerAssignment assignment =
                examinerAssignmentRepository
                        .findByExaminerIdAndExamSessionId(
                                examiner.getId(),
                                examSessionId
                        )
                        .orElseThrow(() -> new IllegalArgumentException(
                                "You are not assigned to this exam session"
                        ));

        List<ExamAnswer> answers =
                examAnswerRepository
                        .findByExamAttemptIdOrderByQuestionIdAsc(attemptId);

        return answers.stream()
                .map(this::mapToResponse)
                .toList();
    }

    private ExaminerAnswerResponse mapToResponse(ExamAnswer answer) {

        return new ExaminerAnswerResponse(
                answer.getId(),
                answer.getExamAttempt().getId(),
                answer.getQuestion().getId(),
                answer.getQuestion().getQuestionText(),
                answer.getSelectedOption().getId(),
                answer.getSelectedOption().getOptionLabel(),
                answer.getSelectedOption().getOptionText(),
                answer.getAnsweredAt()
        );
    }
}