package com.certification.exam_system.dto.exam;

import java.time.LocalDateTime;

public class ExamResultResponse {

    private Long resultId;
    private Long attemptId;

    private Integer totalQuestions;
    private Integer answeredQuestions;
    private Integer correctAnswers;
    private Integer wrongAnswers;

    private Integer totalMarks;
    private Integer obtainedMarks;

    private Double percentage;
    private Boolean passed;

    private LocalDateTime evaluatedAt;

    public ExamResultResponse() {
    }

    public ExamResultResponse(
            Long resultId,
            Long attemptId,
            Integer totalQuestions,
            Integer answeredQuestions,
            Integer correctAnswers,
            Integer wrongAnswers,
            Integer totalMarks,
            Integer obtainedMarks,
            Double percentage,
            Boolean passed,
            LocalDateTime evaluatedAt
    ) {
        this.resultId = resultId;
        this.attemptId = attemptId;
        this.totalQuestions = totalQuestions;
        this.answeredQuestions = answeredQuestions;
        this.correctAnswers = correctAnswers;
        this.wrongAnswers = wrongAnswers;
        this.totalMarks = totalMarks;
        this.obtainedMarks = obtainedMarks;
        this.percentage = percentage;
        this.passed = passed;
        this.evaluatedAt = evaluatedAt;
    }

    public Long getResultId() {
        return resultId;
    }

    public Long getAttemptId() {
        return attemptId;
    }

    public Integer getTotalQuestions() {
        return totalQuestions;
    }

    public Integer getAnsweredQuestions() {
        return answeredQuestions;
    }

    public Integer getCorrectAnswers() {
        return correctAnswers;
    }

    public Integer getWrongAnswers() {
        return wrongAnswers;
    }

    public Integer getTotalMarks() {
        return totalMarks;
    }

    public Integer getObtainedMarks() {
        return obtainedMarks;
    }

    public Double getPercentage() {
        return percentage;
    }

    public Boolean getPassed() {
        return passed;
    }

    public LocalDateTime getEvaluatedAt() {
        return evaluatedAt;
    }
}