package com.certification.exam_system.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "exam_assignments",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_candidate_exam_session",
                        columnNames = {
                                "candidate_id",
                                "exam_session_id"
                        }
                )
        }
)
public class ExamAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "candidate_id",
            nullable = false
    )
    private Candidate candidate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "exam_session_id",
            nullable = false
    )
    private ExamSession examSession;

    @Column(
            nullable = false,
            updatable = false
    )
    private LocalDateTime assignedAt;

    @PrePersist
    protected void onCreate() {
        assignedAt = LocalDateTime.now();
    }

    public ExamAssignment() {
    }

    public Long getId() {
        return id;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
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