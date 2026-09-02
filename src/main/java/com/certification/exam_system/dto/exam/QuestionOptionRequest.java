package com.certification.exam_system.dto.exam;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class QuestionOptionRequest {

    @NotBlank(message = "Option label is required")
    @Size(
            max = 10,
            message = "Option label cannot exceed 10 characters"
    )
    private String optionLabel;

    @NotBlank(message = "Option text is required")
    @Size(
            max = 1000,
            message = "Option text cannot exceed 1000 characters"
    )
    private String optionText;

    @NotNull(message = "Correct status is required")
    private Boolean correct;

    public QuestionOptionRequest() {
    }

    public String getOptionLabel() {
        return optionLabel;
    }

    public void setOptionLabel(String optionLabel) {
        this.optionLabel = optionLabel;
    }

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    public Boolean getCorrect() {
        return correct;
    }

    public void setCorrect(Boolean correct) {
        this.correct = correct;
    }
}