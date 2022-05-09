package com.sahd.Internetbanking.controller;

import com.sahd.Internetbanking.entity.User;
import com.sahd.Internetbanking.payload.response.ErrorResponse;
import com.sahd.Internetbanking.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class UserControllerIntegrationTest {

    @Autowired
    private UserController userController;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Test getting all users")
    void testGetAll() {
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok(preparedUsers());
        ResponseEntity<Object> actualResponse = userController.getAll();
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("Test add user")
    void testAddUser() {
        User newUser = new User();
        newUser.setName("Viktorija Secret");

        ResponseEntity<Object> actualResponse = userController.addUser(newUser);

        Long userId = userRepository.findAllByName("Viktorija Secret").stream().findFirst().orElse(new User(-1L, "")).getId();
        newUser.setId(userId);
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok(newUser);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("Test get user and update")
    void testUpdateUser() {
        User user = new User(1L, "Viktorija Secret");
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok(user);

        ResponseEntity<Object> actualResponse = userController.updateUser(user);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("Test delete user and test exception")
    void testDeleteUserAndCheckForExist() {
        ResponseEntity<Object> actualResponse = userController.deleteUser(1L);
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok(null);
        assertEquals(expectedResponse, actualResponse);

        ResponseEntity<Object> expectedResponseCheck = ResponseEntity.badRequest().body(new ErrorResponse(0, "User not found by id= 1"));
        ResponseEntity<Object> actualResponseCheck = userController.getUserById(1L);
        assertEquals(expectedResponseCheck, actualResponseCheck);
    }

    @Test
    @DisplayName("Test exception: update nonexistent user")
    void testDeleteNonExistUser() {
        ResponseEntity<Object> actualResponse = userController.updateUser(new User(-1L, ""));
        ResponseEntity<Object> expectedResponse = ResponseEntity.badRequest().body(new ErrorResponse(0, "User not found by id= -1"));
        assertEquals(expectedResponse, actualResponse);
    }


    private List<User> preparedUsers() {
        return Arrays.asList(
                new User(1L, "John Malkovich"),
                new User(2L, "Paul McKinley"),
                new User(3L, "Christopher Robertson"),
                new User(4L, "Edvard Schwarts")
        );
    }
}