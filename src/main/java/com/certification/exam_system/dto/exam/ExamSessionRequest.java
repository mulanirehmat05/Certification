package com.certification.exam_system.dto.exam;

import com.certification.exam_system.entity.ExamMode;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalTime;

public class ExamSessionRequest {

    @NotBlank(message = "Session code is required")
    @Size(
            max = 50,
            message = "Session code cannot exceed 50 characters"
    )
    private String sessionCode;

    @NotNull(message = "Certification ID is required")
    private Long certificationId;

    @NotNull(message = "Exam date is required")
    @FutureOrPresent(
            message = "Exam date cannot be in the past"
    )
    private LocalDate examDate;

    @NotNull(message = "Start time is required")
    private LocalTime startTime;

    @NotNull(message = "End time is required")
    private LocalTime endTime;

    @NotNull(message = "Exam mode is required")
    private ExamMode examMode;

    @Size(
            max = 255,
            message = "Exam center cannot exceed 255 characters"
    )
    private String examCenter;

    @Size(
            max = 500,
            message = "Exam center address cannot exceed 500 characters"
    )
    private String examCenterAddress;

    @NotNull(message = "Maximum candidates is required")
    @Min(
            value = 1,
            message = "Maximum candidates must be at least 1"
    )
    private Integer maximumCandidates;

    public ExamSessionRequest() {
    }

    public String getSessionCode() {
        return sessionCode;
    }

    public void setSessionCode(String sessionCode) {
        this.sessionCode = sessionCode;
    }

    public Long getCertificationId() {
        return certificationId;
    }

    public void setCertificationId(Long certificationId) {
        this.certificationId = certificationId;
    }

    public LocalDate getExamDate() {
        return examDate;
    }

    public void setExamDate(LocalDate examDate) {
        this.examDate = examDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public ExamMode getExamMode() {
        return examMode;
    }

    public void setExamMode(ExamMode examMode) {
        this.examMode = examMode;
    }

    public String getExamCenter() {
        return examCenter;
    }

    public void setExamCenter(String examCenter) {
        this.examCenter = examCenter;
    }

    public String getExamCenterAddress() {
        return examCenterAddress;
    }

    public void setExamCenterAddress(String examCenterAddress) {
        this.examCenterAddress = examCenterAddress;
    }

    public Integer getMaximumCandidates() {
        return maximumCandidates;
    }

    public void setMaximumCandidates(Integer maximumCandidates) {
        this.maximumCandidates = maximumCandidates;
    }
}