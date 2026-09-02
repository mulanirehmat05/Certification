package com.certification.exam_system.dto.exam;

import com.certification.exam_system.entity.QuestionDifficulty;
import com.certification.exam_system.entity.QuestionType;

import java.time.LocalDateTime;
import java.util.List;

public class QuestionResponse {

    private Long id;

    private Long certificationId;
    private String certificationCode;
    private String certificationName;

    private String questionText;
    private QuestionType questionType;
    private QuestionDifficulty difficulty;

    private Integer marks;
    private Boolean active;

    private List<QuestionOptionResponse> options;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public QuestionResponse() {
    }

    public QuestionResponse(
            Long id,
            Long certificationId,
            String certificationCode,
            String certificationName,
            String questionText,
            QuestionType questionType,
            QuestionDifficulty difficulty,
            Integer marks,
            Boolean active,
            List<QuestionOptionResponse> options,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.certificationId = certificationId;
        this.certificationCode = certificationCode;
        this.certificationName = certificationName;
        this.questionText = questionText;
        this.questionType = questionType;
        this.difficulty = difficulty;
        this.marks = marks;
        this.active = active;
        this.options = options;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public Long getCertificationId() {
        return certificationId;
    }

    public String getCertificationCode() {
        return certificationCode;
    }

    public String getCertificationName() {
        return certificationName;
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

    public Boolean getActive() {
        return active;
    }

    public List<QuestionOptionResponse> getOptions() {
        return options;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}