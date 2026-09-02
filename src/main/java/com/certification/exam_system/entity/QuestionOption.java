package com.certification.exam_system.entity;

import jakarta.persistence.*;

@Entity
@Table(
        name = "question_options",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_question_option_label",
                        columnNames = {
                                "question_id",
                                "option_label"
                        }
                )
        }
)
public class QuestionOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "question_id",
            nullable = false
    )
    private Question question;

    @Column(
            name = "option_label",
            nullable = false,
            length = 10
    )
    private String optionLabel;

    @Column(
            name = "option_text",
            nullable = false,
            length = 1000
    )
    private String optionText;

    @Column(
            name = "is_correct",
            nullable = false
    )
    private Boolean correct = false;

    public QuestionOption() {
    }

    public Long getId() {
        return id;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
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