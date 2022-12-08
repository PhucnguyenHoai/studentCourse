package org.example.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.example.model.Course;
import org.example.repository.CourseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/course")
@CrossOrigin
public class CourseController {
    private final Logger logger = LoggerFactory.getLogger(Course.class);

    @Autowired
    private CourseRepository courseRepository;

    private boolean check(Course course) {
        boolean a = true;
        if (course.isFlag() == true) {
            a = true;
        } else {
            a = false;
        }
        return a;
    }

    @GetMapping
    public List<Course> getAllCourse() {
        List<Course> courseList = courseRepository.findAll();

        return courseList.stream()
                .filter(course -> course.isFlag())
                .collect(Collectors.toList());
    }

    @GetMapping("/search/{id}")
    public Course getCourseById(@PathVariable("id") Long id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid id: " + id));
        if (!check(course)) {
            throw new NoSuchElementException("Currently, user has flag is false");
        } else {
            return course;
        }
    }

    @PostMapping
    public ResponseEntity<Course> addCourse(@RequestBody Course course) {
        try {
            Course newCourse = courseRepository.save(course);
            return new ResponseEntity<>(newCourse, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable("id") Long id, @RequestBody Course course) {
        Course findCourse = courseRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid id: " + id));

        if (check(findCourse)) {
            Course newCourse = findCourse;

            newCourse.setName(course.getName());

            return new ResponseEntity<>(courseRepository.save(newCourse), HttpStatus.OK);
        } else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable("id") Long id) {
        try {
            Course user = courseRepository.findById(id).get();
            if (check(user)) {
                courseRepository.deleteCourseById(id);
            } else {
                logger.error("Currently, user has flag is false");
            }
        } catch (Exception e) {
            throw e;
        }
    }

}
