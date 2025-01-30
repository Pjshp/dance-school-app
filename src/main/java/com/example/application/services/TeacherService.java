package com.example.application.services;

import com.example.application.data.Teacher;
import com.example.application.data.TeacherRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TeacherService {
    private final TeacherRepository repository;

    @Autowired
    public TeacherService(TeacherRepository repository) {
        this.repository = repository;
    }

    public List<Teacher> findAll() {
        return repository.findAll();
    }

    public Optional<Teacher> findById(Long id) {
        return repository.findById(id);
    }

    public Optional<Teacher> findByLastName(String lastName) {
        return repository.findByLastName(lastName);
    }

    public Teacher save(Teacher teacher) {
        return repository.save(teacher);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}