package com.certification.exam_system.dto.exam;

public class QuestionOptionResponse {

    private Long id;
    private String optionLabel;
    private String optionText;

    public QuestionOptionResponse() {
    }

    public QuestionOptionResponse(
            Long id,
            String optionLabel,
            String optionText
    ) {
        this.id = id;
        this.optionLabel = optionLabel;
        this.optionText = optionText;
    }

    public Long getId() {
        return id;
    }

    public String getOptionLabel() {
        return optionLabel;
    }

    public String getOptionText() {
        return optionText;
    }
}