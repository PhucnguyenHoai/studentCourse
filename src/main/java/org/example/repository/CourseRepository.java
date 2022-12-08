package org.example.repository;

import org.example.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    @Transactional
    @Modifying
    @Query(value = "UPDATE Course c\n" +
            "SET c.flag = false\n" +
            "WHERE c.id = :id")
    void deleteCourseById(@Param("id") Long id);
}
