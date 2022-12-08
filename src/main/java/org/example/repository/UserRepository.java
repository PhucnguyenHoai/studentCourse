package org.example.repository;

import java.util.List;
import org.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "select u.id, u.email, u.name, u.flag\n" +
            "from USER_TBL u LEFT JOIN COURSE_TBL c ON u.id =  c.user_id\n" +
            "where c.id = :id", nativeQuery = true)
    List<User> getUserByCourseId(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE User u\n" +
            "SET u.flag = false\n" +
            "WHERE u.id = :id")
    void rejectUserById(@Param("id") Long id);
}
