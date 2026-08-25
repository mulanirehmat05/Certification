package com.certification.exam_system.dto.candidate;

import com.certification.exam_system.entity.VerificationStatus;

import java.time.LocalDate;

public class CandidateResponse {

    private Long id;
    private Long userId;
    private String candidateCode;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String phone;
    private String address;
    private VerificationStatus verificationStatus;

    public CandidateResponse() {
    }

    public CandidateResponse(
            Long id,
            Long userId,
            String candidateCode,
            String firstName,
            String lastName,
            LocalDate dateOfBirth,
            String phone,
            String address,
            VerificationStatus verificationStatus
    ) {
        this.id = id;
        this.userId = userId;
        this.candidateCode = candidateCode;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.address = address;
        this.verificationStatus = verificationStatus;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getCandidateCode() {
        return candidateCode;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public VerificationStatus getVerificationStatus() {
        return verificationStatus;
    }
}