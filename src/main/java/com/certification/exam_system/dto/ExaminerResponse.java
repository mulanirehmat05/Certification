package com.certification.exam_system.dto;

public class ExaminerResponse {

    private Long id;
    private String username;
    private String email;
    private Boolean active;

    public ExaminerResponse() {
    }

    public ExaminerResponse(
            Long id,
            String username,
            String email,
            Boolean active
    ) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getActive() {
        return active;
    }
}