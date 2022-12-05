package org.example.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.hibernate.mapping.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/user")
public class UserController {
    private final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/getAll")
    public List<User> getAllUser() {
        List<User> userList = userRepository.findAll();
        List<User> aList = userList.stream().filter(user -> user.isFlag()).collect(Collectors.toList());
        return aList;
    }

    @GetMapping("/search/{id}")
    public User getUserById(@PathVariable("id") Long id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user id: " + id));
    }

    @GetMapping("/course/{id}")
    public List<User> getUserByCourseId(@PathVariable("id") Long id) {
        return userRepository.getUserByCourseId(id);
    }

    @PostMapping
    public ResponseEntity<? extends Object> addUser(@RequestBody User user) {
        try {
            User newUser = userRepository.save(user);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<? extends Object> editUser(@PathVariable("id") Long id, @RequestBody User user) {
        Optional<User> findUser = userRepository.findById(id);

        if (findUser.isPresent()) {
            User newUser = findUser.get();

            newUser.setName(user.getName());
            newUser.setEmail(user.getEmail());
            newUser.setFlag(user.isFlag());

            return new ResponseEntity<>(userRepository.save(newUser), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        try {
            userRepository.deleteById(id);
            return "Deleted";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
