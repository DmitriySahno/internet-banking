package com.sahd.Internetbanking.service;

import com.sahd.Internetbanking.entity.Balance;
import com.sahd.Internetbanking.exception.BalanceNotEnoughException;
import com.sahd.Internetbanking.exception.BalanceNotFoundException;
import com.sahd.Internetbanking.exception.UserNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class BalanceServiceTest {

    @Autowired
    private BalanceService balanceService;

    @Test
    @DisplayName("Test get and decrease user balance")
    public void testGetAndDecreaseBalance() {
        Balance currentBalance = balanceService.getBalance(2L);
        assertEquals(2000, currentBalance.getAmount());

        balanceService.decreaseBalance(2L, 500D);

        currentBalance = balanceService.getBalance(2L);
        assertEquals(1500, currentBalance.getAmount());
    }

    @Test
    @DisplayName("Test get and increase user balance")
    public void testGetAndIncreaseBalance() {
        Balance currentBalance = balanceService.getBalance(1L);
        assertEquals(120, currentBalance.getAmount());

        balanceService.increaseBalance(1L, 30D);

        currentBalance = balanceService.getBalance(1L);
        assertEquals(150, currentBalance.getAmount());
    }

    @Test
    @DisplayName("Test exception: user not found")
    public void testUserNotFoundException(){
        Exception exception = assertThrows(UserNotFoundException.class, () -> balanceService.getBalance(-1L));

        String expectedMessage = "User not found by id= -1";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Test exception: balance not found")
    public void testBalanceNotFoundException(){
        String expectedMessage = "Error while getting Balance by userId= 3";
        String actualMessageGet = assertThrows(BalanceNotFoundException.class, () -> balanceService.getBalance(3L)).getMessage();
        assertTrue(actualMessageGet.contains(expectedMessage));

        String actualMessageInc = assertThrows(BalanceNotFoundException.class, () -> balanceService.increaseBalance(3L, 0D)).getMessage();
        assertTrue(actualMessageInc.contains(expectedMessage));

        String actualMessageDec = assertThrows(BalanceNotFoundException.class, () -> balanceService.decreaseBalance(3L, 0D)).getMessage();
        assertTrue(actualMessageDec.contains(expectedMessage));
    }

    @Test
    @DisplayName("Test exception: balance not enough")
    public void testBalanceNotEnoughException() {
        Exception exception = assertThrows(BalanceNotEnoughException.class, () -> balanceService.decreaseBalance(1L, 999999D));

        String expectedMessage = "Not enough facilities on Balance of user: 1";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }


}