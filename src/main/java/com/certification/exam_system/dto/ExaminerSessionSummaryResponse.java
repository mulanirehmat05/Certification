package com.certification.exam_system.dto;

public class ExaminerSessionSummaryResponse {

    private Long examSessionId;
    private String sessionCode;

    private Integer totalCandidates;
    private Integer attemptsStarted;
    private Integer attemptsInProgress;
    private Integer attemptsSubmitted;
    private Integer attemptsEvaluated;

    public ExaminerSessionSummaryResponse() {
    }

    public ExaminerSessionSummaryResponse(
            Long examSessionId,
            String sessionCode,
            Integer totalCandidates,
            Integer attemptsStarted,
            Integer attemptsInProgress,
            Integer attemptsSubmitted,
            Integer attemptsEvaluated
    ) {
        this.examSessionId = examSessionId;
        this.sessionCode = sessionCode;
        this.totalCandidates = totalCandidates;
        this.attemptsStarted = attemptsStarted;
        this.attemptsInProgress = attemptsInProgress;
        this.attemptsSubmitted = attemptsSubmitted;
        this.attemptsEvaluated = attemptsEvaluated;
    }

    public Long getExamSessionId() {
        return examSessionId;
    }

    public String getSessionCode() {
        return sessionCode;
    }

    public Integer getTotalCandidates() {
        return totalCandidates;
    }

    public Integer getAttemptsStarted() {
        return attemptsStarted;
    }

    public Integer getAttemptsInProgress() {
        return attemptsInProgress;
    }

    public Integer getAttemptsSubmitted() {
        return attemptsSubmitted;
    }

    public Integer getAttemptsEvaluated() {
        return attemptsEvaluated;
    }
}