package com.example.college_management.dto;

import com.example.college_management.entity.Course;
import java.time.LocalDate;

public class ExamDto {
    private String subject;
    private LocalDate examDate;
    private Course course;
    private String duration;
    private String title;

    public ExamDto() {}

    public ExamDto(String subject, LocalDate examDate, String duration) {
        this.subject = subject;
        this.examDate = examDate;
        this.duration = duration;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public LocalDate getExamDate() {
        return examDate;
    }

    public void setExamDate(LocalDate examDate) {
        this.examDate = examDate;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "ExamDto{subject='" + subject + "', date=" + examDate + "}";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
