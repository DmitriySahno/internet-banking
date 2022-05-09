package com.sahd.Internetbanking.service;

import com.sahd.Internetbanking.entity.Balance;
import com.sahd.Internetbanking.entity.Operation;
import com.sahd.Internetbanking.entity.User;
import com.sahd.Internetbanking.enums.OperationType;
import com.sahd.Internetbanking.exception.BalanceNotEnoughException;
import com.sahd.Internetbanking.exception.OperationNotFoundException;
import com.sahd.Internetbanking.exception.UserNotFoundException;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class OperationServiceTest {

    @Autowired
    private BalanceService balanceService;
    @Autowired
    private OperationService operationService;

    @Test
    @DisplayName("Test get all operations")
    public void testGetAllOperations() {
        assertTrue(operationService.getAllOperations().size() > 0);
    }

    @Test
    @DisplayName("Test take money")
    public void testTakeMoneyAndCheckBalance() {
        operationService.takeMoney(1L, 20D);
        Balance balance = balanceService.getBalance(1L);
        assertEquals(100, balance.getAmount());
    }

    @Test
    @DisplayName("Test put money")
    public void testPutMoney() {
        operationService.putMoney(1L, 20D);
        Balance balance = balanceService.getBalance(1L);
        assertEquals(140, balance.getAmount());
    }

    @Test
    @DisplayName("Test transfer money")
    public void testTransferMoney() {
        operationService.transferMoney(2L, 1L, 100D);
        Balance balanceTo = balanceService.getBalance(1L);
        Balance balanceFrom = balanceService.getBalance(2L);
        assertEquals(220, balanceTo.getAmount());
        assertEquals(1900, balanceFrom.getAmount());
    }

    @Test
    @DisplayName("Test exception: balance not enough")
    public void testBalanceNotEnoughException() {
        assertThrows(BalanceNotEnoughException.class, () -> operationService.transferMoney(2L, 1L, 999999D));
        assertThrows(BalanceNotEnoughException.class, () -> operationService.takeMoney(1L, 999999D));
    }

    @Test
    @DisplayName("Test get operations with both dates")
    public void testGetOperations() {
        User user = new User(1L, "John Malkovich");
        List<Operation> expectedOperations = Collections.singletonList(
                new Operation(1L, user, OperationType.DEPOSIT,100D, LocalDateTime.parse("2022-01-01T10:00:00"))
        );

        List<Operation> actualOperations = operationService.getOperations(1l, LocalDateTime.parse("2022-01-01T10:00:00"), LocalDateTime.parse("2022-01-01T11:00:00"));

        assertEquals(expectedOperations, actualOperations);
    }

    @Test
    @DisplayName("Test get operations by start date")
    public void testGetOperationsByStartDate() {
        User user = new User(1L, "John Malkovich");
        List<Operation> expectedOperations = Collections.singletonList(new Operation(2L, user,
                OperationType.DEPOSIT,20D, LocalDateTime.parse("2022-01-01T12:00:00")));

        List<Operation> actualOperations = operationService.getOperations(1L,
                LocalDateTime.parse("2022-01-01T11:00:00"), null);

        assertEquals(expectedOperations, actualOperations);
    }

    @Test
    @DisplayName("Test get operations by end date")
    public void testGetOperationsByEndDate() {
        User user = new User(1L, "John Malkovich");
        List<Operation> expectedOperations = Collections.singletonList(new Operation(1L, user,
                OperationType.DEPOSIT,100D, LocalDateTime.parse("2022-01-01T10:00:00")));

        List<Operation> actualOperations = operationService.getOperations(1L,
                null, LocalDateTime.parse("2022-01-01T11:00:00"));

        assertEquals(expectedOperations, actualOperations);
    }

    @Test
    @DisplayName("Test get operations w/o dates")
    public void testGetOperationsWithoutDates() {
        User user = new User(1L, "John Malkovich");
        List<Operation> expectedOperations = new ArrayList<>();
        expectedOperations.add(new Operation(1L, user,OperationType.DEPOSIT,100D, LocalDateTime.parse("2022-01-01T10:00:00")));
        expectedOperations.add(new Operation(2L, user,OperationType.DEPOSIT,20D, LocalDateTime.parse("2022-01-01T12:00:00")));

        List<Operation> actualOperations = operationService.getOperations(1L,null, null);

        assertEquals(expectedOperations, actualOperations);
    }

    @Test
    @DisplayName("Test exception: operations by nonexistent user")
    public void testOperationsByNonExistUser() {
        assertThrows(UserNotFoundException.class, () -> operationService.putMoney(-1L, 0D));
        assertThrows(UserNotFoundException.class, () -> operationService.takeMoney(-1L, 0D));
        assertThrows(UserNotFoundException.class, () -> operationService.getOperations(-1L, null, null));

        assertThrows(UserNotFoundException.class, () -> operationService.transferMoney(-1L, 2L, 0D));
        assertThrows(UserNotFoundException.class, () -> operationService.transferMoney(1L, -1L, 0D));
        assertThrows(UserNotFoundException.class, () -> operationService.transferMoney(-1L, -1L, 0D));
    }

    @Test
    @DisplayName("Test exception: operation not found")
    public void testOperationsNotFound() {
        assertThrows(OperationNotFoundException.class, () -> operationService.deleteOperation(new Operation(-1L, new User(1L, ""),
                OperationType.DEPOSIT, 0D, LocalDateTime.now())));
    }



}