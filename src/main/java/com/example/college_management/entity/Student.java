package com.example.college_management.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("STUDENT")
public class Student extends User {
    private String rollNo;
    private String department;
    private String imageUrl;

    //Added ManyToMany relation with Course
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "student_courses",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private Set<Course> courses = new HashSet<>();

    public Student() {}

    public Student(String name, String email, String password, String contact, String rollNo, String department, String imageUrl) {
        super(name, email, password, contact);
        this.rollNo = rollNo;
        this.department = department;
        this.imageUrl = imageUrl;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    //Getters and Setters for Courses
    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    //Utility method to easily add a course
    public void enrollInCourse(Course course) {
        this.courses.add(course);
        course.getStudents().add(this);
    }

    @Override
    public String toString() {
        return "Student{id=" + getId() + ", name='" + getName() + "', rollNo='" + rollNo + "'}";
    }
}
