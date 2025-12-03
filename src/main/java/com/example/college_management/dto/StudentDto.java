package com.example.college_management.dto;

public class StudentDto {
    private String name;       //  FIX: use "name" to match JSON + DB
    private String email;
    private String contact;
    private String password;
    private String rollNo;
    private String department;
    private String imageUrl;
    private String username;

    public StudentDto(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public StudentDto() {
    }

    public StudentDto(String name, String email, String contact, String password, String rollNo, String imageUrl) {
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.password = password;
        this.rollNo = rollNo;
        this.imageUrl = imageUrl;
    }


    //Proper getters and setters
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "StudentDto{name='" + name + "', rollNo='" + rollNo + "'}";
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
