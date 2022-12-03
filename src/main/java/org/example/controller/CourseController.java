package org.example.controller;

import org.example.model.Course;
import org.example.model.Student;
import org.example.repository.CourseRepository;
import org.example.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1")
public class CourseController {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/getAllCourse")
    public List<Course> getAllCourse() {
        return courseRepository.findAll();
    }

    @GetMapping("/searchCourse/{id}")
    public Course getCourseById(@PathVariable("id") Long id) {
        return courseRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Invalid course id: " + id)
        );
    }

    @PostMapping()
    public ResponseEntity<Course> addCourse(@RequestBody Course course) {
        try {
            Course newCourse = courseRepository.save(course);
            return new ResponseEntity<>(newCourse, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Student> addStudentWithCourse(@RequestBody Student student) {
        try {
            Student newStudent = studentRepository.save(student);
            return new ResponseEntity<>(newStudent, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/student/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable("id") Long id, @RequestBody Student student) {
        Optional<Student> findStudent = studentRepository.findById(id);

        if (findStudent.isPresent()) {
            Student newStudent = findStudent.get();

            newStudent.setAge(student.getAge());
            newStudent.setCourses(student.getCourses());
            newStudent.setLevel(student.getLevel());
            newStudent.setName(student.getName());
            return new ResponseEntity<>(studentRepository.save(newStudent), HttpStatus.OK);
        } else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/course/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable("id") Long id, @RequestBody Course course) {
        Optional<Course> findCourse = courseRepository.findById(id);

        if (findCourse.isPresent()) {
            Course newCourse = findCourse.get();

            newCourse.setAbbreviation(course.getAbbreviation());
            newCourse.setStudents(course.getStudents());
            newCourse.setFee(course.getFee());
            newCourse.setTitle(course.getTitle());
            return new ResponseEntity<>(courseRepository.save(newCourse), HttpStatus.OK);
        } else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/student/{id}")
    public ResponseEntity<HttpStatus> deleteStudent(@PathVariable("id") long id) {
        try {
            studentRepository.deleteByIdAllIgnoreCase(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
