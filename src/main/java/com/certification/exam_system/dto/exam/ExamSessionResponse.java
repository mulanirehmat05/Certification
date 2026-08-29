package com.certification.exam_system.dto.exam;

import com.certification.exam_system.entity.ExamMode;
import com.certification.exam_system.entity.ExamSessionStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ExamSessionResponse {

    private Long id;
    private String sessionCode;

    private Long certificationId;
    private String certificationCode;
    private String certificationName;

    private LocalDate examDate;
    private LocalTime startTime;
    private LocalTime endTime;

    private ExamMode examMode;
    private ExamSessionStatus status;

    private String examCenter;
    private String examCenterAddress;

    private Integer maximumCandidates;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ExamSessionResponse() {
    }

    public ExamSessionResponse(
            Long id,
            String sessionCode,
            Long certificationId,
            String certificationCode,
            String certificationName,
            LocalDate examDate,
            LocalTime startTime,
            LocalTime endTime,
            ExamMode examMode,
            ExamSessionStatus status,
            String examCenter,
            String examCenterAddress,
            Integer maximumCandidates,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.sessionCode = sessionCode;
        this.certificationId = certificationId;
        this.certificationCode = certificationCode;
        this.certificationName = certificationName;
        this.examDate = examDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.examMode = examMode;
        this.status = status;
        this.examCenter = examCenter;
        this.examCenterAddress = examCenterAddress;
        this.maximumCandidates = maximumCandidates;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public String getSessionCode() {
        return sessionCode;
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

    public LocalDate getExamDate() {
        return examDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public ExamMode getExamMode() {
        return examMode;
    }

    public ExamSessionStatus getStatus() {
        return status;
    }

    public String getExamCenter() {
        return examCenter;
    }

    public String getExamCenterAddress() {
        return examCenterAddress;
    }

    public Integer getMaximumCandidates() {
        return maximumCandidates;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}