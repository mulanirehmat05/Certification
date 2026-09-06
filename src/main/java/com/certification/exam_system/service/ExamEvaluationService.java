package com.certification.exam_system.service;

import com.certification.exam_system.dto.exam.ExamResultResponse;
import com.certification.exam_system.entity.ExamAnswer;
import com.certification.exam_system.entity.ExamAttempt;
import com.certification.exam_system.entity.ExamAttemptStatus;
import com.certification.exam_system.entity.ExamResult;
import com.certification.exam_system.entity.ExamSessionQuestion;
import com.certification.exam_system.repository.ExamAnswerRepository;
import com.certification.exam_system.repository.ExamAttemptRepository;
import com.certification.exam_system.repository.ExamResultRepository;
import com.certification.exam_system.repository.ExamSessionQuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExamEvaluationService {

    private final ExamAttemptRepository examAttemptRepository;
    private final ExamAnswerRepository examAnswerRepository;
    private final ExamSessionQuestionRepository examSessionQuestionRepository;
    private final ExamResultRepository examResultRepository;

    public ExamEvaluationService(
            ExamAttemptRepository examAttemptRepository,
            ExamAnswerRepository examAnswerRepository,
            ExamSessionQuestionRepository examSessionQuestionRepository,
            ExamResultRepository examResultRepository
    ) {
        this.examAttemptRepository = examAttemptRepository;
        this.examAnswerRepository = examAnswerRepository;
        this.examSessionQuestionRepository = examSessionQuestionRepository;
        this.examResultRepository = examResultRepository;
    }

    @Transactional
    public ExamResultResponse evaluateExam(Long attemptId) {

        ExamAttempt examAttempt =
                examAttemptRepository
                        .findById(attemptId)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Exam attempt not found"
                                )
                        );

        if (examAttempt.getStatus()
                != ExamAttemptStatus.SUBMITTED) {

            throw new IllegalArgumentException(
                    "Exam attempt must be submitted before evaluation"
            );
        }

        if (examResultRepository
                .existsByExamAttemptId(attemptId)) {

            throw new IllegalArgumentException(
                    "Exam attempt has already been evaluated"
            );
        }

        Long examSessionId =
                examAttempt.getExamSession().getId();

        List<ExamSessionQuestion> sessionQuestions =
                examSessionQuestionRepository
                        .findByExamSessionIdOrderByQuestionOrderAsc(
                                examSessionId
                        );

        List<ExamAnswer> answers =
                examAnswerRepository
                        .findByExamAttemptId(attemptId);

        int totalQuestions =
                sessionQuestions.size();

        int answeredQuestions =
                answers.size();

        int correctAnswers = 0;
        int wrongAnswers = 0;
        int totalMarks = 0;
        int obtainedMarks = 0;

        // Calculate total marks
        for (ExamSessionQuestion sessionQuestion :
                sessionQuestions) {

            totalMarks +=
                    sessionQuestion
                            .getQuestion()
                            .getMarks();
        }

        // Calculate correct and wrong answers
        for (ExamAnswer answer : answers) {

            if (answer.getSelectedOption().getCorrect()) {

                correctAnswers++;

                obtainedMarks +=
                        answer.getQuestion().getMarks();

            } else {

                wrongAnswers++;
            }
        }

        // Calculate percentage
        double percentage = 0.0;

        if (totalMarks > 0) {

            percentage =
                    ((double) obtainedMarks / totalMarks) * 100;
        }

        // Get passing percentage
        double passingPercentage =
                examAttempt
                        .getExamSession()
                        .getCertification()
                        .getPassingPercentage();

        // Determine pass/fail
        boolean passed =
                percentage >= passingPercentage;

        // Create result
        ExamResult examResult =
                new ExamResult();

        examResult.setExamAttempt(examAttempt);
        examResult.setTotalQuestions(totalQuestions);
        examResult.setAnsweredQuestions(answeredQuestions);
        examResult.setCorrectAnswers(correctAnswers);
        examResult.setWrongAnswers(wrongAnswers);
        examResult.setTotalMarks(totalMarks);
        examResult.setObtainedMarks(obtainedMarks);
        examResult.setPercentage(percentage);
        examResult.setPassed(passed);
        examResult.setEvaluatedAt(LocalDateTime.now());

        // Save result
        ExamResult savedResult =
                examResultRepository.save(examResult);

        // Change attempt status
        examAttempt.setStatus(
                ExamAttemptStatus.EVALUATED
        );

        examAttemptRepository.save(examAttempt);

        // Return DTO
        return new ExamResultResponse(
                savedResult.getId(),
                examAttempt.getId(),
                savedResult.getTotalQuestions(),
                savedResult.getAnsweredQuestions(),
                savedResult.getCorrectAnswers(),
                savedResult.getWrongAnswers(),
                savedResult.getTotalMarks(),
                savedResult.getObtainedMarks(),
                savedResult.getPercentage(),
                savedResult.getPassed(),
                savedResult.getEvaluatedAt()
        );
    }
}