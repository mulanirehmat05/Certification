package com.certification.exam_system.dto.exam;

public class ExamSessionQuestionResponse {

    private Long id;

    private Long examSessionId;
    private String sessionCode;

    private Long questionId;
    private String questionText;

    private Integer questionOrder;

    public ExamSessionQuestionResponse() {
    }

    public ExamSessionQuestionResponse(
            Long id,
            Long examSessionId,
            String sessionCode,
            Long questionId,
            String questionText,
            Integer questionOrder
    ) {
        this.id = id;
        this.examSessionId = examSessionId;
        this.sessionCode = sessionCode;
        this.questionId = questionId;
        this.questionText = questionText;
        this.questionOrder = questionOrder;
    }

    public Long getId() {
        return id;
    }

    public Long getExamSessionId() {
        return examSessionId;
    }

    public String getSessionCode() {
        return sessionCode;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public Integer getQuestionOrder() {
        return questionOrder;
    }
}