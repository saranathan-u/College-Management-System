package com.example.college_management.controller;

import com.example.college_management.config.JwtUtils;
import com.example.college_management.dto.AuthRequest;
import com.example.college_management.dto.AuthResponse;
import com.example.college_management.dto.StudentDto;
import com.example.college_management.entity.Student;
import com.example.college_management.service.CustomUserDetailsService;
import com.example.college_management.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = {
        "http://127.0.0.1:5500",
        "http://localhost:5500",
        "http://localhost:3000"
})
public class StudentController {
    private final StudentService studentService;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;

    @Autowired
    public StudentController(StudentService studentService,
                             JwtUtils jwtUtils,
                             @Qualifier("studentAuthManager") AuthenticationManager authenticationManager,
                             CustomUserDetailsService customUserDetailsService) {
        this.studentService = studentService;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
        this.customUserDetailsService = customUserDetailsService;
    }

    //Register new student
    @PostMapping("/register")
    public ResponseEntity<?> registerStudent(@RequestBody StudentDto studentDto) {
        try {
            Student student = studentService.registerStudent(studentDto);
            return ResponseEntity.ok(student);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Registration failed: " + e.getMessage());
        }
    }

    //Login and generate JWT token
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            String input = authRequest.getUsernameOrEmail();
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            input,
                            authRequest.getPassword()
                    )
            );
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(input);
            String token = jwtUtils.generateToken(userDetails);
            System.out.println("🎟️ JWT token generated for: " + input);
            System.out.println("🔑 Token: " + token);
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(401).body("Invalid username or password");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Login failed: " + e.getMessage());
        }
    }

    //Get logged-in student details
    @GetMapping("/me")
    public ResponseEntity<?> getStudentProfile(@RequestHeader("Authorization") String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(401).body("Missing or invalid Authorization header");
            }
            String token = authHeader.substring(7).trim();
            String email = jwtUtils.extractUsername(token); // ✅ This extracts from the JWT subject
            if (email == null) {
                return ResponseEntity.status(401).body("Invalid token or username not found in token");
            }
            Student student = studentService.getStudentByEmail(email);
            if (student == null) {
                return ResponseEntity.status(404).body("Student not found for email: " + email);
            }
            return ResponseEntity.ok(student);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    //Get student by ID
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    //Get all students
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    //Update student by ID (admin use)
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody StudentDto studentDto) {
        return ResponseEntity.ok(studentService.updateStudent(id, studentDto));
    }

    //Upload or update photo only
    @PostMapping("/me/upload-photo")
    public ResponseEntity<String> uploadMyPhoto(
            @RequestHeader("Authorization") String token,
            @RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }
        Student student = studentService.getStudentFromToken(token);
        String uploadDir = "uploads/";
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path path = Paths.get(uploadDir + fileName);
        Files.createDirectories(path.getParent());
        Files.write(path, file.getBytes());
        String imageUrl = "http://localhost:8080/uploads/" + fileName;
        student.setImageUrl(imageUrl);
        studentService.saveStudent(student);
        return ResponseEntity.ok(imageUrl);
    }

    //NEW: Update both details and photo together
    @PostMapping("/me/update-profile")
    public ResponseEntity<?> updateStudentProfile(
            @RequestHeader("Authorization") String authHeader,
            @RequestPart("student") StudentDto studentDto,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        try {
            String token = authHeader.replace("Bearer ", "").trim();
            String email = jwtUtils.extractUsername(token);
            if (email == null) {
                return ResponseEntity.status(401).body("Invalid token");
            }
            Student student = studentService.getStudentByEmail(email);
            if (student == null) {
                return ResponseEntity.status(404).body("Student not found");
            }

            //Update details
            if (studentDto.getName() != null) student.setName(studentDto.getName());
            if (studentDto.getContact() != null) student.setContact(studentDto.getContact());
            if (studentDto.getDepartment() != null) student.setDepartment(studentDto.getDepartment());
            if (studentDto.getRollNo() != null) student.setRollNo(studentDto.getRollNo());

            //Handle photo upload
            if (file != null && !file.isEmpty()) {
                String uploadDir = "uploads/";
                String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                Path path = Paths.get(uploadDir + fileName);
                Files.createDirectories(path.getParent());
                Files.write(path, file.getBytes());
                String imageUrl = "http://localhost:8080/uploads/" + fileName;
                student.setImageUrl(imageUrl);
            }
            studentService.saveStudent(student);
            return ResponseEntity.ok(student);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Profile update failed: " + e.getMessage());
        }
    }

    //Enroll student in course (via ID)
    @PostMapping("/{studentId}/enroll/{courseId}")
    public ResponseEntity<String> enrollStudentInCourse(
            @PathVariable Long studentId,
            @PathVariable Long courseId) {
        studentService.enrollStudentInCourse(studentId, courseId);
        return ResponseEntity.ok("Student enrolled successfully!");
    }

    //Enroll logged-in student in course
    @PostMapping("/enroll")
    public ResponseEntity<String> enrollCourse(
            @RequestHeader("Authorization") String token,
            @RequestParam Long courseId) {
        try {
            Student student = studentService.getStudentFromToken(token);
            studentService.enrollStudentInCourse(student.getId(), courseId);
            return ResponseEntity.ok("Enrolled successfully in the course!");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Enrollment failed: " + e.getMessage());
        }
    }
}