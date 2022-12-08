package org.example.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.example.model.User;
import org.example.repository.UserRepository;
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
@RequestMapping("/v1/user")
@CrossOrigin
public class UserController {
    private final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserRepository userRepository;

    private boolean check(User user) {
        boolean a = true;
            if (user.isFlag() == true) {
                a = true;
            } else {
                a = false;
            }
        return a;
    }

    @GetMapping
    public List<User> getAllUser() {
        List<User> userList = userRepository.findAll();
        return userList.stream().filter(user -> user.isFlag()).collect(Collectors.toList());
    }

    @GetMapping("/search/{id}")
    public User getUserById(@PathVariable("id") Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid id: " + id));
        if(check(user)) {
            return user;
        } else {
            throw new IllegalArgumentException("Invalid id: " + id);
        }
    }

    @GetMapping("/course/{id}")
    public List<User> getUserByCourseId(@PathVariable("id") Long id) {
        List<User> user = userRepository.getUserByCourseId(id);
        List<User> list = user.stream().filter(u -> u.isFlag()).collect(Collectors.toList());
        return list;
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
        User findUser = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid id: " + id));

        if (check(findUser)) {
            User newUser = findUser;

            newUser.setName(user.getName());
            newUser.setEmail(user.getEmail());
            newUser.setFlag(user.isFlag());

            return new ResponseEntity<>(userRepository.save(newUser), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        try {
            User user = userRepository.findById(id).get();
            if (check(user)) {
                userRepository.rejectUserById(id);
            } else {
                log.error("Currently, user has flag is false");
            }
        } catch (Exception e) {
            throw e;
        }
    }
}
