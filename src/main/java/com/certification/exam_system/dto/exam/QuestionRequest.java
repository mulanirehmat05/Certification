package com.certification.exam_system.dto.exam;

import com.certification.exam_system.entity.QuestionDifficulty;
import com.certification.exam_system.entity.QuestionType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public class QuestionRequest {

    @NotNull(message = "Certification ID is required")
    private Long certificationId;

    @NotBlank(message = "Question text is required")
    @Size(
            max = 2000,
            message = "Question text cannot exceed 2000 characters"
    )
    private String questionText;

    @NotNull(message = "Question type is required")
    private QuestionType questionType;

    @NotNull(message = "Difficulty is required")
    private QuestionDifficulty difficulty;

    @NotNull(message = "Marks are required")
    @Min(
            value = 1,
            message = "Marks must be at least 1"
    )
    private Integer marks;

    @NotNull(message = "Active status is required")
    private Boolean active;

    @NotEmpty(message = "At least one option is required")
    @Valid
    private List<QuestionOptionRequest> options;

    public QuestionRequest() {
    }

    public Long getCertificationId() {
        return certificationId;
    }

    public void setCertificationId(Long certificationId) {
        this.certificationId = certificationId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public QuestionDifficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(
            QuestionDifficulty difficulty
    ) {
        this.difficulty = difficulty;
    }

    public Integer getMarks() {
        return marks;
    }

    public void setMarks(Integer marks) {
        this.marks = marks;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<QuestionOptionRequest> getOptions() {
        return options;
    }

    public void setOptions(
            List<QuestionOptionRequest> options
    ) {
        this.options = options;
    }
}