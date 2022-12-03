package org.example.repository;

import org.example.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    @Transactional
    @Modifying
    @Query("delete from Student s where s.id = :id")
    void deleteByIdAllIgnoreCase(@Param("id") Long id);
}
