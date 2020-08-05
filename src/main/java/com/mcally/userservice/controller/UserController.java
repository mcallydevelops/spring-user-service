package com.mcally.userservice.controller;

import com.mcally.userservice.model.User;
import com.mcally.userservice.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<Iterable<User>> retrieveUsers() {
        Iterable<User> userIterable = userRepository.findAll();
        return ResponseEntity.ok(userIterable);
    }

}
