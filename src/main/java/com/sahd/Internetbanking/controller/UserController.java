package com.sahd.Internetbanking.controller;

import com.sahd.Internetbanking.entity.User;
import com.sahd.Internetbanking.payload.response.ErrorResponse;
import com.sahd.Internetbanking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<Object> getAll() {
        try {
            List<User> users = userService.getUsers();
            return ResponseEntity.ok(users);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(0, e.getMessage()));
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getUserById(@PathVariable Long userId) {
        try {
            User user = userService.getUserById(userId);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(0, e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<Object> addUser(@RequestBody User user) {
        try {
            User savedUser = userService.addUser(user);
            return ResponseEntity.ok(savedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(0, e.getMessage()));
        }
    }

    @PutMapping
    public ResponseEntity<Object> updateUser(@RequestBody User user) {
        try {
            User updatedUser = userService.updateUser(user);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(0, e.getMessage()));
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok(null);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(0, e.getMessage()));
        }
    }
}
