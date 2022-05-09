package com.sahd.Internetbanking.service;

import com.sahd.Internetbanking.entity.Balance;
import com.sahd.Internetbanking.entity.User;
import com.sahd.Internetbanking.exception.BalanceNotEnoughException;
import com.sahd.Internetbanking.exception.BalanceNotFoundException;
import com.sahd.Internetbanking.exception.UserNotFoundException;
import com.sahd.Internetbanking.repository.BalanceRepository;
import com.sahd.Internetbanking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BalanceService {

    private final BalanceRepository balanceRepository;
    private final UserRepository userRepository;

    public Balance getBalance(Long userId) {
        log.info("Starting to get Balance by userId= " + userId);
        Optional<User> userById = userRepository.findById(userId);
        if (userById.isEmpty()) {
            log.error("User not found by id= " + userId);
            throw new UserNotFoundException("User not found by id= " + userId);
        }
        Optional<Balance> balanceById = balanceRepository.findById(userId);
        if (balanceById.isEmpty()) {
            log.error("Error while getting Balance by userId= " + userId);
            throw new BalanceNotFoundException("Error while getting Balance by userId= " + userId);
        }
        log.info("Balance was got successfully by userId= " + userId);
        return balanceById.get();
    }

    public Balance decreaseBalance(Long userId, Double amount) {
        log.info("Starting to decrease Balance by userId: {}, amount: {} ", userId, amount);
        Optional<Balance> balanceById = balanceRepository.findById(userId);
        if (balanceById.isEmpty()) {
            log.error("Error while getting Balance by userId= " + userId);
            throw new BalanceNotFoundException("Error while getting Balance by userId= " + userId);
        }
        Balance currentBalance = balanceById.get();
        if (currentBalance.getAmount() < amount) {
            log.error("Not enough facilities on Balance of user: {}, balance: {}, required: {}", userId, currentBalance.getAmount(), amount);
            throw new BalanceNotEnoughException("Not enough facilities on Balance of user: " + userId + ", balance: " + currentBalance.getAmount() + ", required: " + amount);
        }
        currentBalance.setAmount(currentBalance.getAmount() - amount);
        balanceRepository.save(currentBalance);
        log.info("Balance was decreased successfully: " + currentBalance);
        return currentBalance;
    }

    public Balance increaseBalance(Long userId, Double amount) {
        log.info("Starting to increase Balance by userId: {}, amount: {} ", userId, amount);
        Optional<Balance> balanceById = balanceRepository.findById(userId);
        if (balanceById.isEmpty()) {
            log.error("Error while getting Balance by userId= " + userId);
            throw new BalanceNotFoundException("Error while getting Balance by userId= " + userId);
        }
        Balance currentBalance = balanceById.get();
        currentBalance.setAmount(currentBalance.getAmount() + amount);
        balanceRepository.save(currentBalance);
        log.info("Balance was decreased successfully: " + currentBalance);
        return currentBalance;
    }


}
