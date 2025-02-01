package com.example.application.services;

import com.example.application.data.Course;
import com.example.application.data.CourseRepository;
import com.example.application.data.Teacher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CourseService {
    private final CourseRepository repository;

    @Autowired
    public CourseService(CourseRepository repository) {
        this.repository = repository;
    }

    public List<Course> findAll() {
        return repository.findAll();
    }

    public Optional<Course> findById(Long id) {
        return repository.findById(id);
    }

    public Optional<Course> findByName(String courseName) {
        return repository.findByCourseName(courseName);
    }

    public Course save(Course course) {
        return repository.save(course);
    }

    public void update(Course course) {
        // Assuming the save method in the repository can handle both insert and update
        repository.save(course);
    }
}