package com.example.application.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByUser(User user);
    boolean existsByUserAndCourse(User user, Course course);
    List<Enrollment> findByCourse(Course course);
    @Query("SELECT e FROM Enrollment e JOIN FETCH e.course c JOIN FETCH c.teacher WHERE e.user.userId = :userId")
    List<Enrollment> findByUserWithCoursesAndTeachers(Long userId);
}