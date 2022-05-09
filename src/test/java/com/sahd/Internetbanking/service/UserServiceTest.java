package com.sahd.Internetbanking.service;

import com.sahd.Internetbanking.entity.Balance;
import com.sahd.Internetbanking.entity.User;
import com.sahd.Internetbanking.exception.UserNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private BalanceService balanceService;

    @Test
    @DisplayName("Test get users")
    public void testGetUsers() {
        List<User> users = userService.getUsers();
        assertFalse(users.isEmpty());
    }

    @Test
    @DisplayName("Test add user and check balance")
    public void testAddUserAndCheckBalances() {
        User newUser = new User();
        newUser.setName("John Petrov");
        User addedUser = userService.addUser(newUser);
        assertEquals(newUser.getName(), addedUser.getName());

        Balance balanceOfAddedUser = balanceService.getBalance(addedUser.getId());
        assertNotNull(balanceOfAddedUser);
    }

    @Test
    @DisplayName("Test update user")
    public void testGetAndUpdateUser() {
        User user = userService.getUsers().get(0);
        user.setName("Viktorija Secret");
        User updatedUser = userService.updateUser(user);
        assertEquals("Viktorija Secret", updatedUser.getName());
        assertTrue(updatedUser.getId() > 0);
    }

    @Test
    @DisplayName("Test delete user")
    public void testGetAndDeleteUser() {
        User user = userService.getUsers().get(0);
        Long userId = user.getId();
        userService.deleteUser(userId);

        Optional<User> deletedUserOpt = userService.getUsers().stream().filter(u -> u.getId() == userId).findFirst();
        assertTrue(deletedUserOpt.isEmpty());

        String actualMessage = assertThrows(UserNotFoundException.class, () -> balanceService.getBalance(userId)).getMessage();
        assertTrue(actualMessage.contains("User not found by id"));
    }

    @Test
    @DisplayName("Test exception: user not found")
    public void testGetAndDeleteUserException() {
        String actualMessageUpd = assertThrows(UserNotFoundException.class, () -> userService.updateUser(new User(-1L, ""))).getMessage();
        String actualMessageDel = assertThrows(UserNotFoundException.class, () -> userService.deleteUser(-1L)).getMessage();

        String expectedMessage = "User not found by id";

        assertTrue(actualMessageUpd.contains(expectedMessage));
        assertTrue(actualMessageDel.contains(expectedMessage));
    }

}