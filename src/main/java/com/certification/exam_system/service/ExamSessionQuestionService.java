package com.certification.exam_system.service;

import com.certification.exam_system.dto.exam.ExamSessionQuestionRequest;
import com.certification.exam_system.dto.exam.ExamSessionQuestionResponse;
import com.certification.exam_system.entity.Certification;
import com.certification.exam_system.entity.ExamSession;
import com.certification.exam_system.entity.ExamSessionQuestion;
import com.certification.exam_system.entity.ExamSessionStatus;
import com.certification.exam_system.entity.Question;
import com.certification.exam_system.repository.ExamSessionQuestionRepository;
import com.certification.exam_system.repository.ExamSessionRepository;
import com.certification.exam_system.repository.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ExamSessionQuestionService {

    private final ExamSessionQuestionRepository
            examSessionQuestionRepository;

    private final ExamSessionRepository
            examSessionRepository;

    private final QuestionRepository
            questionRepository;

    public ExamSessionQuestionService(
            ExamSessionQuestionRepository examSessionQuestionRepository,
            ExamSessionRepository examSessionRepository,
            QuestionRepository questionRepository
    ) {
        this.examSessionQuestionRepository =
                examSessionQuestionRepository;

        this.examSessionRepository =
                examSessionRepository;

        this.questionRepository =
                questionRepository;
    }

    @Transactional
    public ExamSessionQuestionResponse addQuestionToExamSession(
            ExamSessionQuestionRequest request
    ) {

        ExamSession examSession =
                examSessionRepository
                        .findById(request.getExamSessionId())
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Exam session not found"
                                )
                        );

        Question question =
                questionRepository
                        .findById(request.getQuestionId())
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Question not found"
                                )
                        );

        if (examSession.getStatus()
                != ExamSessionStatus.SCHEDULED) {

            throw new IllegalArgumentException(
                    "Questions can only be added to a scheduled exam session"
            );
        }

        if (!Boolean.TRUE.equals(question.getActive())) {

            throw new IllegalArgumentException(
                    "Only active questions can be added to an exam session"
            );
        }

        if (examSessionQuestionRepository
                .existsByExamSessionIdAndQuestionId(
                        request.getExamSessionId(),
                        request.getQuestionId()
                )) {

            throw new IllegalArgumentException(
                    "Question is already assigned to this exam session"
            );
        }

        Certification sessionCertification =
                examSession.getCertification();

        Certification questionCertification =
                question.getCertification();

        if (!sessionCertification.getId()
                .equals(questionCertification.getId())) {

            throw new IllegalArgumentException(
                    "Question does not belong to the certification of this exam session"
            );
        }

        List<ExamSessionQuestion> existingQuestions =
                examSessionQuestionRepository
                        .findByExamSessionIdOrderByQuestionOrderAsc(
                                request.getExamSessionId()
                        );

        boolean orderAlreadyUsed =
                existingQuestions
                        .stream()
                        .anyMatch(existing ->
                                existing.getQuestionOrder()
                                        .equals(
                                                request.getQuestionOrder()
                                        )
                        );

        if (orderAlreadyUsed) {

            throw new IllegalArgumentException(
                    "Question order is already used in this exam session"
            );
        }

        ExamSessionQuestion examSessionQuestion =
                new ExamSessionQuestion();

        examSessionQuestion.setExamSession(examSession);
        examSessionQuestion.setQuestion(question);
        examSessionQuestion.setQuestionOrder(
                request.getQuestionOrder()
        );

        ExamSessionQuestion saved =
                examSessionQuestionRepository.save(
                        examSessionQuestion
                );

        return mapToResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<ExamSessionQuestionResponse>
    getQuestionsByExamSession(
            Long examSessionId
    ) {

        if (!examSessionRepository.existsById(
                examSessionId
        )) {

            throw new IllegalArgumentException(
                    "Exam session not found"
            );
        }

        return examSessionQuestionRepository
                .findByExamSessionIdOrderByQuestionOrderAsc(
                        examSessionId
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ExamSessionQuestionResponse
    getExamSessionQuestionById(
            Long id
    ) {

        ExamSessionQuestion examSessionQuestion =
                examSessionQuestionRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Exam session question not found"
                                )
                        );

        return mapToResponse(examSessionQuestion);
    }

    @Transactional
    public void removeQuestionFromExamSession(
            Long id
    ) {

        ExamSessionQuestion examSessionQuestion =
                examSessionQuestionRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Exam session question not found"
                                )
                        );

        examSessionQuestionRepository.delete(
                examSessionQuestion
        );
    }

    private ExamSessionQuestionResponse mapToResponse(
            ExamSessionQuestion examSessionQuestion
    ) {

        ExamSession examSession =
                examSessionQuestion.getExamSession();

        Question question =
                examSessionQuestion.getQuestion();

        return new ExamSessionQuestionResponse(
                examSessionQuestion.getId(),
                examSession.getId(),
                examSession.getSessionCode(),
                question.getId(),
                question.getQuestionText(),
                examSessionQuestion.getQuestionOrder()
        );
    }
}