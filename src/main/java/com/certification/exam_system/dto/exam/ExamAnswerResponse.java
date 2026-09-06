package com.certification.exam_system.dto.exam;

public class ExamAnswerResponse {

    private Long answerId;
    private Long attemptId;
    private Long questionId;
    private Long selectedOptionId;
    private String message;

    public ExamAnswerResponse() {
    }

    public ExamAnswerResponse(
            Long answerId,
            Long attemptId,
            Long questionId,
            Long selectedOptionId,
            String message
    ) {
        this.answerId = answerId;
        this.attemptId = attemptId;
        this.questionId = questionId;
        this.selectedOptionId = selectedOptionId;
        this.message = message;
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

    public Long getSelectedOptionId() {
        return selectedOptionId;
    }

    public String getMessage() {
        return message;
    }
}