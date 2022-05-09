package com.sahd.Internetbanking.service;

import com.sahd.Internetbanking.entity.Balance;
import com.sahd.Internetbanking.entity.User;
import com.sahd.Internetbanking.exception.UserNotFoundException;
import com.sahd.Internetbanking.repository.BalanceRepository;
import com.sahd.Internetbanking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final BalanceRepository balanceRepository;
    private final OperationService operationService;

    public List<User> getUsers() {
        log.info("Starting to get all users");
        List<User> users = userRepository.findAll();
        log.info("Users were got successfully");
        return users;
    }

    public User getUserById(Long userId) {
        log.info("Starting to get all user by id= " + userId);
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            log.info("User not found by id= " + userId);
            throw new UserNotFoundException("User not found by id= " + userId);
        }
        log.info("User was got successfully: " + userOpt.get());
        return userOpt.get();
    }

    @Transactional
    public User addUser(User user) {
        log.info("Starting to save user: {}", user);
        User savedUser = userRepository.save(user);
        balanceRepository.save(new Balance(savedUser.getId(), 0.0));
        log.info("User was saved successfully: {}", savedUser);
        return savedUser;
    }

    public User updateUser(User user) {
        log.info("Starting to update user: {}", user);
        Optional<User> userById = userRepository.findById(user.getId());
        if (userById.isEmpty()) {
            log.error("User not found by id= " + user.getId());
            throw new UserNotFoundException("User not found by id= " + user.getId());
        }
        User savedUser = userRepository.save(user);
        log.info("User was updated successfully: {}", savedUser);
        return savedUser;
    }

    @Transactional
    public void deleteUser(Long userId) {
        log.info("Starting to delete user by id= " + userId);
        Optional<User> userById = userRepository.findById(userId);
        if (userById.isEmpty()) {
            log.error("User not found by id=" + userId);
            throw new UserNotFoundException("User not found by id=" + userId);
        }
        operationService.getOperations(userId, null, null).forEach(operationService::deleteOperation);
        userRepository.deleteById(userId);
        balanceRepository.deleteById(userId);
        log.info("User was deleted successfully");
    }

}
