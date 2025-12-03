package com.example.college_management.repository;

import com.example.college_management.entity.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    boolean existsByEmail(String email);
    Optional<Faculty> findByEmail(String email);
}