package com.example.application.services;

import com.example.application.data.Course;
import com.example.application.data.Enrollment;
import com.example.application.data.EnrollmentRepository;
import com.example.application.data.User;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    public Enrollment enrollUserInCourse(User user, Course course) {
        Enrollment enrollment = new Enrollment();
        enrollment.setUser(user);
        enrollment.setCourse(course);
        enrollment.setPrice(course.getPrice()); // Set course price
        enrollment.setEnrollmentDate(LocalDate.now());

        return enrollmentRepository.save(enrollment);
    }

    public List<Enrollment> findByUser(User user) {
        List<Enrollment> enrollments = enrollmentRepository.findByUser(user);
        enrollments.forEach(enrollment -> Hibernate.initialize(enrollment.getCourse()));
        return enrollments;
    }

    public Optional<Enrollment> findByUserAndCourse(User user, Course course) {
        Optional<Enrollment> enrollment = enrollmentRepository.findByUserAndCourse(user, course);
        enrollment.ifPresent(e -> Hibernate.initialize(e.getCourse()));
        return enrollment;
    }

    public boolean isUserEnrolledInCourse(User user, Course course) {
        return enrollmentRepository.existsByUserAndCourse(user, course);
    }

    public List<User> getUsersEnrolledInCourse(Course course) {
        List<Enrollment> enrollments = enrollmentRepository.findByCourse(course);
        enrollments.forEach(enrollment -> Hibernate.initialize(enrollment.getUser()));
        return enrollments.stream().map(Enrollment::getUser).collect(Collectors.toList());
    }

    public Enrollment save(Enrollment enrollment) {
        return enrollmentRepository.save(enrollment);
    }

    public void delete(Enrollment enrollment) {
        enrollmentRepository.delete(enrollment);
    }
}