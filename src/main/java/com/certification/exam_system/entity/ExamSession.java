package com.certification.exam_system.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(
        name = "exam_sessions",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_exam_session_code",
                        columnNames = "session_code"
                )
        }
)
public class ExamSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "session_code",
            nullable = false,
            length = 50
    )
    private String sessionCode;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "certification_id",
            nullable = false
    )
    private Certification certification;

    @Column(
            nullable = false
    )
    private LocalDate examDate;

    @Column(
            nullable = false
    )
    private LocalTime startTime;

    @Column(
            nullable = false
    )
    private LocalTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(
            nullable = false,
            length = 20
    )
    private ExamMode examMode;

    @Enumerated(EnumType.STRING)
    @Column(
            nullable = false,
            length = 20
    )
    private ExamSessionStatus status =
            ExamSessionStatus.DRAFT;

    @Column(
            length = 255
    )
    private String examCenter;

    @Column(
            length = 500
    )
    private String examCenterAddress;

    @Column(
            nullable = false
    )
    private Integer maximumCandidates;

    @Column(
            nullable = false,
            updatable = false
    )
    private LocalDateTime createdAt;

    @Column(
            nullable = false
    )
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {

        LocalDateTime now =
                LocalDateTime.now();

        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {

        updatedAt =
                LocalDateTime.now();
    }

    public ExamSession() {
    }

    public Long getId() {
        return id;
    }

    public String getSessionCode() {
        return sessionCode;
    }

    public void setSessionCode(String sessionCode) {
        this.sessionCode = sessionCode;
    }

    public Certification getCertification() {
        return certification;
    }

    public void setCertification(
            Certification certification
    ) {
        this.certification = certification;
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

    public ExamSessionStatus getStatus() {
        return status;
    }

    public void setStatus(
            ExamSessionStatus status
    ) {
        this.status = status;
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

    public void setExamCenterAddress(
            String examCenterAddress
    ) {
        this.examCenterAddress =
                examCenterAddress;
    }

    public Integer getMaximumCandidates() {
        return maximumCandidates;
    }

    public void setMaximumCandidates(
            Integer maximumCandidates
    ) {
        this.maximumCandidates =
                maximumCandidates;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}