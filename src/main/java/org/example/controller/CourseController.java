package org.example.controller;

import java.util.List;
import java.util.Optional;
import org.example.model.Course;
import org.example.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/course")
public class CourseController {
    @Autowired
    private CourseRepository courseRepository;

    @GetMapping("/getAll")
    public List<Course> getAllCourse() {
        return courseRepository.findAll();
    }

    @GetMapping("/search/{id}")
    public Course getCourseById(@PathVariable("id") Long id) {
        return courseRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Invalid course id: " + id)
        );
    }

    @PostMapping
    public ResponseEntity<Course> addCourse(@RequestBody Course course) {
        try {
            Course newCourse = courseRepository.save(course);
            System.out.println(newCourse.toString() + "-----------------");
            return new ResponseEntity<>(newCourse, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable("id") Long id, @RequestBody Course course) {
        Optional<Course> findCourse = courseRepository.findById(id);

        if (findCourse.isPresent()) {
            Course newCourse = findCourse.get();

            newCourse.setName(course.getName());
            return new ResponseEntity<>(courseRepository.save(newCourse), HttpStatus.OK);
        } else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public String deleteCourse(@PathVariable("id") Long id) {
        try {
            courseRepository.deleteById(id);
            return "Deleted";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

}
