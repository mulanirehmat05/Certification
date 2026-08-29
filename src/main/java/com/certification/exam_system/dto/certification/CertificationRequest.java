package com.certification.exam_system.dto.certification;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class CertificationRequest {

    @NotBlank(message = "Certification code is required")
    @Size(
            max = 30,
            message = "Certification code cannot exceed 30 characters"
    )
    private String code;

    @NotBlank(message = "Certification name is required")
    @Size(
            max = 150,
            message = "Certification name cannot exceed 150 characters"
    )
    private String name;

    @Size(
            max = 1000,
            message = "Description cannot exceed 1000 characters"
    )
    private String description;

    @NotNull(message = "Category ID is required")
    private Long categoryId;

    @NotNull(message = "Fee is required")
    @DecimalMin(
            value = "0.0",
            inclusive = true,
            message = "Fee cannot be negative"
    )
    private BigDecimal fee;

    @NotNull(message = "Validity period is required")
    @Min(
            value = 1,
            message = "Validity must be at least 1 month"
    )
    private Integer validityInMonths;

    @NotNull(message = "Passing percentage is required")
    @Min(
            value = 1,
            message = "Passing percentage must be at least 1"
    )
    @Max(
            value = 100,
            message = "Passing percentage cannot exceed 100"
    )
    private Integer passingPercentage;

    @NotNull(message = "Exam duration is required")
    @Min(
            value = 1,
            message = "Exam duration must be at least 1 minute"
    )
    private Integer examDurationMinutes;

    @NotNull(message = "Active status is required")
    private Boolean active;

    @NotBlank(message = "Eligibility criteria is required")
    @Size(
            max = 1000,
            message = "Eligibility criteria cannot exceed 1000 characters"
    )
    private String eligibilityCriteria;

    public CertificationRequest() {
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

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
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

    public void setValidityInMonths(Integer validityInMonths) {
        this.validityInMonths = validityInMonths;
    }

    public Integer getPassingPercentage() {
        return passingPercentage;
    }

    public void setPassingPercentage(Integer passingPercentage) {
        this.passingPercentage = passingPercentage;
    }

    public Integer getExamDurationMinutes() {
        return examDurationMinutes;
    }

    public void setExamDurationMinutes(Integer examDurationMinutes) {
        this.examDurationMinutes = examDurationMinutes;
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

    public void setEligibilityCriteria(String eligibilityCriteria) {
        this.eligibilityCriteria = eligibilityCriteria;
    }
}