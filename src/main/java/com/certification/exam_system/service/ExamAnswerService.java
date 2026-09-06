package com.certification.exam_system.service;

import com.certification.exam_system.dto.exam.ExamAnswerRequest;
import com.certification.exam_system.dto.exam.ExamAnswerResponse;
import com.certification.exam_system.entity.Candidate;
import com.certification.exam_system.entity.ExamAnswer;
import com.certification.exam_system.entity.ExamAttempt;
import com.certification.exam_system.entity.ExamAttemptStatus;
import com.certification.exam_system.entity.ExamSessionQuestion;
import com.certification.exam_system.entity.Question;
import com.certification.exam_system.entity.QuestionOption;
import com.certification.exam_system.entity.User;
import com.certification.exam_system.repository.CandidateRepository;
import com.certification.exam_system.repository.ExamAnswerRepository;
import com.certification.exam_system.repository.ExamAttemptRepository;
import com.certification.exam_system.repository.ExamSessionQuestionRepository;
import com.certification.exam_system.repository.QuestionOptionRepository;
import com.certification.exam_system.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ExamAnswerService {

    private final ExamAnswerRepository examAnswerRepository;
    private final ExamAttemptRepository examAttemptRepository;
    private final ExamSessionQuestionRepository examSessionQuestionRepository;
    private final QuestionOptionRepository questionOptionRepository;
    private final CandidateRepository candidateRepository;
    private final UserRepository userRepository;

    public ExamAnswerService(
            ExamAnswerRepository examAnswerRepository,
            ExamAttemptRepository examAttemptRepository,
            ExamSessionQuestionRepository examSessionQuestionRepository,
            QuestionOptionRepository questionOptionRepository,
            CandidateRepository candidateRepository,
            UserRepository userRepository
    ) {
        this.examAnswerRepository = examAnswerRepository;
        this.examAttemptRepository = examAttemptRepository;
        this.examSessionQuestionRepository = examSessionQuestionRepository;
        this.questionOptionRepository = questionOptionRepository;
        this.candidateRepository = candidateRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ExamAnswerResponse saveAnswer(
            ExamAnswerRequest request
    ) {

        Candidate candidate = getAuthenticatedCandidate();

        ExamAttempt examAttempt =
                examAttemptRepository
                        .findById(request.getAttemptId())
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Exam attempt not found"
                                )
                        );

        if (!examAttempt.getCandidate().getId()
                .equals(candidate.getId())) {

            throw new IllegalArgumentException(
                    "You are not allowed to answer this exam attempt"
            );
        }

        if (examAttempt.getStatus()
                != ExamAttemptStatus.IN_PROGRESS) {

            throw new IllegalArgumentException(
                    "Exam attempt is not in progress"
            );
        }

        ExamSessionQuestion sessionQuestion =
                examSessionQuestionRepository
                        .findByExamSessionIdAndQuestionId(
                                examAttempt.getExamSession().getId(),
                                request.getQuestionId()
                        )
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Question is not assigned to this exam session"
                                )
                        );

        Question question =
                sessionQuestion.getQuestion();

        QuestionOption selectedOption =
                questionOptionRepository
                        .findById(request.getSelectedOptionId())
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Selected option not found"
                                )
                        );

        if (!selectedOption.getQuestion().getId()
                .equals(question.getId())) {

            throw new IllegalArgumentException(
                    "Selected option does not belong to this question"
            );
        }

        ExamAnswer examAnswer =
                examAnswerRepository
                        .findByExamAttemptIdAndQuestionId(
                                examAttempt.getId(),
                                question.getId()
                        )
                        .orElseGet(ExamAnswer::new);

        examAnswer.setExamAttempt(examAttempt);
        examAnswer.setQuestion(question);
        examAnswer.setSelectedOption(selectedOption);
        examAnswer.setAnsweredAt(LocalDateTime.now());

        ExamAnswer savedAnswer =
                examAnswerRepository.save(examAnswer);

        return new ExamAnswerResponse(
                savedAnswer.getId(),
                examAttempt.getId(),
                question.getId(),
                selectedOption.getId(),
                "Answer saved successfully"
        );
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
}