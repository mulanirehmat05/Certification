package com.certification.exam_system.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "certifications",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_certification_code",
                        columnNames = "code"
                )
        }
)
public class Certification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            nullable = false,
            length = 30
    )
    private String code;

    @Column(
            nullable = false,
            length = 150
    )
    private String name;

    @Column(
            length = 1000
    )
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "category_id",
            nullable = false
    )
    private CertificationCategory category;

    @Column(
            nullable = false,
            precision = 10,
            scale = 2
    )
    private BigDecimal fee;

    @Column(
            nullable = false
    )
    private Integer validityInMonths;

    @Column(
            nullable = false
    )
    private Integer passingPercentage;

    @Column(
            nullable = false
    )
    private Integer examDurationMinutes;

    @Column(
            nullable = false
    )
    private Boolean active = true;

    @Column(
            nullable = false,
            length = 1000
    )
    private String eligibilityCriteria;

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

    public Certification() {
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(
            String description
    ) {
        this.description = description;
    }

    public CertificationCategory getCategory() {
        return category;
    }

    public void setCategory(
            CertificationCategory category
    ) {
        this.category = category;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public Integer getValidityInMonths() {
        return validityInMonths;
    }

    public void setValidityInMonths(
            Integer validityInMonths
    ) {
        this.validityInMonths =
                validityInMonths;
    }

    public Integer getPassingPercentage() {
        return passingPercentage;
    }

    public void setPassingPercentage(
            Integer passingPercentage
    ) {
        this.passingPercentage =
                passingPercentage;
    }

    public Integer getExamDurationMinutes() {
        return examDurationMinutes;
    }

    public void setExamDurationMinutes(
            Integer examDurationMinutes
    ) {
        this.examDurationMinutes =
                examDurationMinutes;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getEligibilityCriteria() {
        return eligibilityCriteria;
    }

    public void setEligibilityCriteria(
            String eligibilityCriteria
    ) {
        this.eligibilityCriteria =
                eligibilityCriteria;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}