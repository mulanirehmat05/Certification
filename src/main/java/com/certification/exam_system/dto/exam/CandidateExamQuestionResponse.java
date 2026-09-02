package com.certification.exam_system.dto.exam;

import com.certification.exam_system.entity.QuestionDifficulty;
import com.certification.exam_system.entity.QuestionType;

import java.util.List;

public class CandidateExamQuestionResponse {

    private Long questionId;
    private String questionText;
    private QuestionType questionType;
    private QuestionDifficulty difficulty;
    private Integer marks;
    private Integer questionOrder;
    private List<QuestionOptionResponse> options;

    public CandidateExamQuestionResponse() {
    }

    public CandidateExamQuestionResponse(
            Long questionId,
            String questionText,
            QuestionType questionType,
            QuestionDifficulty difficulty,
            Integer marks,
            Integer questionOrder,
            List<QuestionOptionResponse> options
    ) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.questionType = questionType;
        this.difficulty = difficulty;
        this.marks = marks;
        this.questionOrder = questionOrder;
        this.options = options;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public QuestionDifficulty getDifficulty() {
        return difficulty;
    }

    public Integer getMarks() {
        return marks;
    }

    public Integer getQuestionOrder() {
        return questionOrder;
    }

    public List<QuestionOptionResponse> getOptions() {
        return options;
    }
}