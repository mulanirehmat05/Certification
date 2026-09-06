package com.certification.exam_system.dto;

import java.time.LocalDateTime;

public class ExaminerAnswerResponse {

    private Long answerId;

    private Long attemptId;

    private Long questionId;
    private String questionText;

    private Long selectedOptionId;
    private String selectedOptionLabel;
    private String selectedOptionText;

    private LocalDateTime answeredAt;

    public ExaminerAnswerResponse() {
    }

    public ExaminerAnswerResponse(
            Long answerId,
            Long attemptId,
            Long questionId,
            String questionText,
            Long selectedOptionId,
            String selectedOptionLabel,
            String selectedOptionText,
            LocalDateTime answeredAt
    ) {
        this.answerId = answerId;
        this.attemptId = attemptId;
        this.questionId = questionId;
        this.questionText = questionText;
        this.selectedOptionId = selectedOptionId;
        this.selectedOptionLabel = selectedOptionLabel;
        this.selectedOptionText = selectedOptionText;
        this.answeredAt = answeredAt;
    }

    public Long getAnswerId() {
        return answerId;
    }

    public Long getAttemptId() {
        return attemptId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public Long getSelectedOptionId() {
        return selectedOptionId;
    }

    public String getSelectedOptionLabel() {
        return selectedOptionLabel;
    }

    public String getSelectedOptionText() {
        return selectedOptionText;
    }

    public LocalDateTime getAnsweredAt() {
        return answeredAt;
    }
}