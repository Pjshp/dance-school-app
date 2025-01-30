package com.example.application.services;

import com.example.application.data.Course;
import com.example.application.data.Enrollment;
import com.example.application.data.EnrollmentRepository;
import com.example.application.data.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    public Enrollment enrollUserInCourse(User user, Course course) {
        Enrollment enrollment = new Enrollment();
        enrollment.setUser(user);
        enrollment.setCourse(course);
        enrollment.setPrice(course.getPrice()); // Ustaw cenę kursu

        return enrollmentRepository.save(enrollment);
    }

    // metoda findByUser
    public List<Enrollment> findByUser(User user) {
        return enrollmentRepository.findByUser(user);
    }

    public boolean isUserEnrolledInCourse(User user, Course course) {
        return enrollmentRepository.existsByUserAndCourse(user, course);
    }

    public List<User> getUsersEnrolledInCourse(Course course) {
        List<Enrollment> enrollments = enrollmentRepository.findByCourse(course);
        List<User> users = enrollments.stream().map(Enrollment::getUser).collect(Collectors.toList());
        users.forEach(user -> System.out.println("User enrolled: " + user.getGuardianFirstName() + " " + user.getGuardianLastName()));
        return users;
    }
}