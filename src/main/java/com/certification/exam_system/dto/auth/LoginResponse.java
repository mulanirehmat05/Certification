package com.certification.exam_system.dto.auth;

public class LoginResponse {

    private String message;
    private String token;
    private String username;
    private String role;

    public LoginResponse() {
    }

    public LoginResponse(
            String message,
            String token,
            String username,
            String role
    ) {
        this.message = message;
        this.token = token;
        this.username = username;
        this.role = role;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }
}