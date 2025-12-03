package com.example.college_management.dto;

public class CourseDto {
    private String title;
    private String description;
    private int credits;
    private String duration;
    private String facultyName;

    public CourseDto() {}
    public CourseDto(String title, String description, int credits, String duration, String facultyName) {
        this.title = title;
        this.description = description;
        this.credits = credits;
        this.duration = duration;
        this.facultyName = facultyName;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public int getCredits() {
        return credits;
    }
    public void setCredits(int credits) {
        this.credits = credits;
    }

    public String getDuration() {
        return duration;
    }
    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "CourseDto{title='" + title + "'}";
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }
}
