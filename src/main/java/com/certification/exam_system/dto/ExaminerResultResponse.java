package com.certification.exam_system.dto;

import java.time.LocalDateTime;

public class ExaminerResultResponse {

    private Long resultId;
    private Long attemptId;

    private Long candidateId;
    private String candidateCode;
    private String candidateName;

    private Long examSessionId;
    private String sessionCode;

    private Integer totalQuestions;
    private Integer answeredQuestions;
    private Integer correctAnswers;
    private Integer wrongAnswers;

    private Integer totalMarks;
    private Integer obtainedMarks;

    private Double percentage;
    private Boolean passed;

    private LocalDateTime evaluatedAt;

    public ExaminerResultResponse() {
    }

    public ExaminerResultResponse(
            Long resultId,
            Long attemptId,
            Long candidateId,
            String candidateCode,
            String candidateName,
            Long examSessionId,
            String sessionCode,
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
        this.candidateId = candidateId;
        this.candidateCode = candidateCode;
        this.candidateName = candidateName;
        this.examSessionId = examSessionId;
        this.sessionCode = sessionCode;
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

    public Long getCandidateId() {
        return candidateId;
    }

    public String getCandidateCode() {
        return candidateCode;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public Long getExamSessionId() {
        return examSessionId;
    }

    public String getSessionCode() {
        return sessionCode;
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