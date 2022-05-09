package com.sahd.Internetbanking.service;

import com.sahd.Internetbanking.entity.Operation;
import com.sahd.Internetbanking.entity.User;
import com.sahd.Internetbanking.enums.OperationType;
import com.sahd.Internetbanking.exception.OperationNotFoundException;
import com.sahd.Internetbanking.exception.UserNotFoundException;
import com.sahd.Internetbanking.repository.OperationRepository;
import com.sahd.Internetbanking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OperationService {

    private final OperationRepository operationRepository;
    private final UserRepository userRepository;
    private final BalanceService balanceService;

    public List<Operation> getAllOperations() {
        log.info("Starting to get all operations");
        List<Operation> operations = operationRepository.findAll();
        if (operations.isEmpty()) {
            log.error("Error while getting all operations");
            throw new OperationNotFoundException("Error while getting all operations");
        }
        log.info("Operations were got successfully");
        return operations;
    }

    @Transactional
    public Operation takeMoney(Long userId, Double amount) {
        log.info("Starting to take money by user: {}, amount: {}", userId, amount);
        Optional<User> userById = userRepository.findById(userId);
        if (userById.isEmpty()) {
            log.error("User not found by id= " + userId);
            throw new UserNotFoundException("User not found by id= " + userId);
        }
        Operation operation = new Operation(userById.get(), OperationType.WITHDRAWAL, amount, LocalDateTime.now());
        Operation savedOperation = operationRepository.save(operation);
        balanceService.decreaseBalance(userId, amount);
        log.info("Money was taken successfully by user: {}, amount: {}", userId, amount);
        return savedOperation;
    }

    @Transactional
    public Operation putMoney(Long userId, Double amount) {
        log.info("Starting to put money by user: {}, amount: {}", userId, amount);
        Optional<User> userById = userRepository.findById(userId);
        if (userById.isEmpty()) {
            log.error("User not found by id= " + userId);
            throw new UserNotFoundException("User not found by id= " + userId);
        }
        Operation operation = new Operation(userById.get(), OperationType.DEPOSIT, amount, LocalDateTime.now());
        Operation savedOperation = operationRepository.save(operation);
        balanceService.increaseBalance(userId, amount);
        log.info("Money was taken successfully by user: {}, amount: {}", userId, amount);
        return savedOperation;
    }

    @Transactional
    public void transferMoney(Long userFromId, Long userToId, Double amount) {
        takeMoney(userFromId, amount);
        putMoney(userToId, amount);
    }


    public List<Operation> getOperations(Long userId, LocalDateTime dateFrom, LocalDateTime dateTo) {
        log.info("Starting to get operations by user: {} between dateFrom: {} and dateTo: {}", userId, dateFrom, dateTo);
        Optional<User> userById = userRepository.findById(userId);
        if (userById.isEmpty()) {
            log.error("User not found by id= " + userId);
            throw new UserNotFoundException("User not found by id= " + userId);
        }
        List<Operation> operations = operationRepository.findAllByUserIdAndDateBetween(userId
                , dateFrom == null ? LocalDateTime.parse("0000-01-01T00:00:00.0") : dateFrom
                , dateTo == null ? LocalDate.now().plusDays(1).atTime(0, 0) : dateTo);
        log.info("Operations were got successfully by user: {} between dateFrom: {} and dateTo: {}. Operations: {}", userId, dateFrom, dateTo, operations);
        return operations;
    }

    public void deleteOperation(Operation operation) {
        log.info("Starting to delete operation: {}", operation);
        Long operationId = operation.getId();
        Optional<Operation> operationOpt = operationRepository.findById(operationId);
        if (operationOpt.isEmpty()) {
            log.error("Operation not found by id= " + operationId);
            throw new OperationNotFoundException("Operation not found by id= " + operationId);
        }
        operationRepository.delete(operationOpt.get());
        log.info("Operation {} was successfully deleted", operationOpt.get());
    }

}
