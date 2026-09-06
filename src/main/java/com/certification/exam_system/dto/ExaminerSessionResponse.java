package com.certification.exam_system.dto;

import com.certification.exam_system.entity.ExamMode;
import com.certification.exam_system.entity.ExamSessionStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public class ExaminerSessionResponse {

    private Long examSessionId;
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

    public ExaminerSessionResponse() {
    }

    public ExaminerSessionResponse(
            Long examSessionId,
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
            String examCenterAddress
    ) {
        this.examSessionId = examSessionId;
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
    }

    public Long getExamSessionId() {
        return examSessionId;
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
}