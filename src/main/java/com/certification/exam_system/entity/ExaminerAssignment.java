package com.certification.exam_system.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "examiner_assignments",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_examiner_session",
                columnNames = {"examiner_id", "exam_session_id"}
        )
)
public class ExaminerAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "examiner_id", nullable = false)
    private User examiner;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "exam_session_id", nullable = false)
    private ExamSession examSession;

    @Column(nullable = false, updatable = false)
    private LocalDateTime assignedAt;

    @PrePersist
    protected void onCreate() {
        assignedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public User getExaminer() {
        return examiner;
    }

    public void setExaminer(User examiner) {
        this.examiner = examiner;
    }

    public ExamSession getExamSession() {
        return examSession;
    }

    public void setExamSession(ExamSession examSession) {
        this.examSession = examSession;
    }

    public LocalDateTime getAssignedAt() {
        return assignedAt;
    }
}