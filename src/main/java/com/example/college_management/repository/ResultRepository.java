package com.example.college_management.repository;

import com.example.college_management.entity.Result;
import com.example.college_management.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ResultRepository extends JpaRepository<Result, Long> {
    List<Result> findByStudent(Student student);
}
