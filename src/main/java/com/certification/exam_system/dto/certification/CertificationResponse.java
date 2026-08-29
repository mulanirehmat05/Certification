package com.certification.exam_system.dto.certification;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CertificationResponse {

    private Long id;
    private String code;
    private String name;
    private String description;

    private Long categoryId;
    private String categoryName;

    private BigDecimal fee;
    private Integer validityInMonths;
    private Integer passingPercentage;
    private Integer examDurationMinutes;

    private Boolean active;
    private String eligibilityCriteria;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CertificationResponse() {
    }

    public CertificationResponse(
            Long id,
            String code,
            String name,
            String description,
            Long categoryId,
            String categoryName,
            BigDecimal fee,
            Integer validityInMonths,
            Integer passingPercentage,
            Integer examDurationMinutes,
            Boolean active,
            String eligibilityCriteria,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.fee = fee;
        this.validityInMonths = validityInMonths;
        this.passingPercentage = passingPercentage;
        this.examDurationMinutes = examDurationMinutes;
        this.active = active;
        this.eligibilityCriteria = eligibilityCriteria;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public Integer getValidityInMonths() {
        return validityInMonths;
    }

    public Integer getPassingPercentage() {
        return passingPercentage;
    }

    public Integer getExamDurationMinutes() {
        return examDurationMinutes;
    }

    public Boolean getActive() {
        return active;
    }

    public String getEligibilityCriteria() {
        return eligibilityCriteria;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}