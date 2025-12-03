package com.example.college_management.repository;


import com.example.college_management.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByRollNo(String rollNo);
    Optional<Student> findByUsername(String username);
    Optional<Student> findByEmail(String email);

}
