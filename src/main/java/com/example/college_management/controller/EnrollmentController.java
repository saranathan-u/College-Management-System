package com.example.college_management.controller;

import com.example.college_management.dto.EnrollmentDto;
import com.example.college_management.entity.Enrollment;
import com.example.college_management.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @Autowired
    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    //NOT needed for student login. (Can delete if unused)
    @PostMapping
    public ResponseEntity<Enrollment> enroll(@RequestBody EnrollmentDto dto) {
        return ResponseEntity.ok(enrollmentService.enrollStudent(dto));
    }

    @GetMapping
    public ResponseEntity<List<Enrollment>> getAllEnrollments() {
        return ResponseEntity.ok(enrollmentService.getAllEnrollments());
    }

    @PostMapping("/enroll")
    public ResponseEntity<String> enrollCourse(
            @RequestHeader("Authorization") String token,
            @RequestBody Map<String, Long> body) {

        Long courseId = body.get("courseId");
        enrollmentService.enrollStudent(token, courseId);

        return ResponseEntity.ok("Enrolled successfully!");
    }

    //LOAD STUDENT ENROLLMENTS
    @GetMapping("/my")
    public ResponseEntity<List<Enrollment>> getMyEnrollments(
            @RequestHeader("Authorization") String token) {

        List<Enrollment> enrollments = enrollmentService.getMyEnrollments(token);
        return ResponseEntity.ok(enrollments);
    }
}
