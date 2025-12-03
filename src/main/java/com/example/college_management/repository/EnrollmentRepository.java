package com.example.college_management.repository;

import com.example.college_management.entity.Course;
import com.example.college_management.entity.Enrollment;
import com.example.college_management.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    // Find all enrollments for a student
    List<Enrollment> findByStudent(Student student);

    // Find all enrollments for a course
    List<Enrollment> findByCourse(Course course);
    List<Enrollment> findByStudentId(Long studentId);

    // Find a specific enrollment of a student in a course
    Optional<Enrollment> findByStudentAndCourse(Student student, Course course);

    // Check if a student is enrolled in a course
    boolean existsByStudentAndCourse(Student student, Course course);
    boolean existsByStudentIdAndCourseId(Long id, Long id1);
}
