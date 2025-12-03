package com.example.college_management.controller;

import com.example.college_management.dto.CourseDto;
import com.example.college_management.entity.Course;
import com.example.college_management.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public ResponseEntity<Course> addCourse(@RequestBody CourseDto dto) {
        return ResponseEntity.ok(courseService.addCourse(dto));
    }

    @PostMapping("/raw")
    public ResponseEntity<Course> addCourse(@RequestBody Course course) {
        return ResponseEntity.ok(courseService.createCourse(course));
    }

    @PostMapping("/register")
    public ResponseEntity<Course> registerCourse(@RequestBody CourseDto dto) {
        return ResponseEntity.ok(courseService.addCourse(dto));
    }

    @PostMapping("/{courseId}/enroll")
    public ResponseEntity<String> enrollCourse(
            @PathVariable Long courseId,
            @RequestHeader("Authorization") String token) {
        boolean enrolled = courseService.enrollStudentInCourse(courseId, token);
        if (enrolled) {
            return ResponseEntity.ok("Successfully enrolled in course!");
        } else {
            return ResponseEntity.status(400).body("Enrollment failed.");
        }
    }

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourse(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }
}
