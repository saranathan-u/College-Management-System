package com.example.college_management.dto;

public class AuthResponse {
    private String token;
    private String role;

    public AuthResponse(String token) {
        this.token = token;
    }

    public AuthResponse(String token, String roleFaculty) {
        this.token = token;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
