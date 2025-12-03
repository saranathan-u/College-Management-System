package com.example.college_management.dto;

public class AuthRequest {
    // For login: use username or email
    private String usernameOrEmail;
    private String password;
    private String name;
    private String email;
    private String contact;

    // Default constructor
    public AuthRequest() {}

    // Constructor for login
    public AuthRequest(String usernameOrEmail, String password) {
        this.usernameOrEmail = usernameOrEmail;
        this.password = password;
    }

    // Constructor for registration
    public AuthRequest(String usernameOrEmail, String password, String name, String email, String contact) {
        this.usernameOrEmail = usernameOrEmail;
        this.password = password;
        this.name = name;
        this.email = email;
        this.contact = contact;
    }

    // Getters and Setters
    public String getUsernameOrEmail() {
        return usernameOrEmail;
    }

    public void setUsernameOrEmail(String usernameOrEmail) {
        this.usernameOrEmail = usernameOrEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
