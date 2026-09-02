package com.certification.exam_system.entity;

import jakarta.persistence.*;

@Entity
@Table(
        name = "exam_session_questions",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_exam_session_question",
                        columnNames = {
                                "exam_session_id",
                                "question_id"
                        }
                )
        }
)
public class ExamSessionQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "exam_session_id",
            nullable = false
    )
    private ExamSession examSession;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "question_id",
            nullable = false
    )
    private Question question;

    @Column(
            name = "question_order",
            nullable = false
    )
    private Integer questionOrder;

    public ExamSessionQuestion() {
    }

    public Long getId() {
        return id;
    }

    public ExamSession getExamSession() {
        return examSession;
    }

    public void setExamSession(ExamSession examSession) {
        this.examSession = examSession;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Integer getQuestionOrder() {
        return questionOrder;
    }

    public void setQuestionOrder(Integer questionOrder) {
        this.questionOrder = questionOrder;
    }
}