package com.certification.exam_system.dto.exam;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ExamSessionQuestionRequest {

    @NotNull(message = "Exam session ID is required")
    private Long examSessionId;

    @NotNull(message = "Question ID is required")
    private Long questionId;

    @NotNull(message = "Question order is required")
    @Min(
            value = 1,
            message = "Question order must be at least 1"
    )
    private Integer questionOrder;

    public ExamSessionQuestionRequest() {
    }

    public Long getExamSessionId() {
        return examSessionId;
    }

    public void setExamSessionId(Long examSessionId) {
        this.examSessionId = examSessionId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Integer getQuestionOrder() {
        return questionOrder;
    }

    public void setQuestionOrder(Integer questionOrder) {
        this.questionOrder = questionOrder;
    }
}