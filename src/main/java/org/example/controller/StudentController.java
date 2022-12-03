package org.example.controller;

import org.example.model.Student;
import org.example.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class StudentController {
    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/getAllStudent")
    public List<Student> getAllStudent() {
        return studentRepository.findAll();
    }

    @GetMapping("/search/{id}")
    private Student getStudentById(@PathVariable("id") Long id) {
        return studentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Invalid user Id:" + id));
    }
}
