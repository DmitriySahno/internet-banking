package com.sahd.Internetbanking.controller;

import com.sahd.Internetbanking.entity.Operation;
import com.sahd.Internetbanking.entity.User;
import com.sahd.Internetbanking.enums.OperationType;
import com.sahd.Internetbanking.payload.request.BaseRequest;
import com.sahd.Internetbanking.payload.request.OperationListRequest;
import com.sahd.Internetbanking.payload.request.TransferMoneyRequest;
import com.sahd.Internetbanking.payload.response.BalanceResponse;
import com.sahd.Internetbanking.payload.response.ErrorResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@ActiveProfiles("test")
@Transactional
class OperationControllerIntegrationTest {

    @Autowired
    private OperationController operationController;

    @Test
    @DisplayName("Test take money")
    public void testTakeMoney() {
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok(new BalanceResponse(1D));
        ResponseEntity<Object> actualResponse = operationController.takeMoney(new BaseRequest(1L, 20D));
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("Test put money")
    public void testPutMoney() {
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok(new BalanceResponse(1D));
        ResponseEntity<Object> actualResponse = operationController.putMoney(new BaseRequest(2L, 2000D));
        assertEquals(expectedResponse, actualResponse);
    }


    @Test
    @DisplayName("Test transfer money")
    public void testTransferMoney() {
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok(new BalanceResponse(1D));
        ResponseEntity<Object> actualResponse = operationController.transferMoney(new TransferMoneyRequest(2L, 1L, 2000D));
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("Test get operations with both dates")
    public void testGetOperationsWithBothDates() {
        User user = new User(1L, "John Malkovich");
        List<Operation> operations = new ArrayList<>();
        operations.add(new Operation(1L, user, OperationType.DEPOSIT, 100D, LocalDateTime.parse("2022-01-01T10:00")));
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok(operations);

        ResponseEntity<Object> actualResponse = operationController.getOperationList(new OperationListRequest(1L,
                LocalDateTime.parse("2022-01-01T10:00:00"), LocalDateTime.parse("2022-01-01T10:59:59")));
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("Test get operations with start date")
    public void testGetOperationsWithStartDate() {
        User user = new User(1L, "John Malkovich");
        List<Operation> operations = new ArrayList<>();
        operations.add(new Operation(2L, user, OperationType.DEPOSIT, 20D, LocalDateTime.parse("2022-01-01T12:00")));
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok(operations);

        ResponseEntity<Object> actualResponse = operationController.getOperationList(new OperationListRequest(1L,
                LocalDateTime.parse("2022-01-01T11:00:00"), null));
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("Test get operations with end date")
    public void testGetOperationsWithEndDate() {
        User user = new User(1L, "John Malkovich");
        List<Operation> operations = new ArrayList<>();
        operations.add(new Operation(1L, user, OperationType.DEPOSIT, 100D, LocalDateTime.parse("2022-01-01T10:00")));
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok(operations);
        ResponseEntity<Object> actualResponse = operationController.getOperationList(new OperationListRequest(1L,
                null, LocalDateTime.parse("2022-01-01T11:00:00")));
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("Test get operations w/o dates")
    public void testGetOperations() {
        User user = new User(1L, "John Malkovich");
        List<Operation> operations = new ArrayList<>();
        operations.add(new Operation(1L, user, OperationType.DEPOSIT, 100D, LocalDateTime.parse("2022-01-01T10:00")));
        operations.add(new Operation(2L, user, OperationType.DEPOSIT, 20D, LocalDateTime.parse("2022-01-01T12:00")));
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok(operations);
        ResponseEntity<Object> actualResponse = operationController.getOperationList(new OperationListRequest(1L, null, null));
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("Test get operations by user w/o operations")
    public void testGetOperationsByUserWithoutOperations() {
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok(new ArrayList<>());
        ResponseEntity<Object> actualResponse = operationController.getOperationList(new OperationListRequest(3L, null, null));
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("Test exception: take non existing money")
    public void testTakeNonExistMoney() {
        ResponseEntity<Object> expectedResponse = ResponseEntity.badRequest().body(new ErrorResponse(0, String.format("Not enough facilities on Balance of user: %s, balance: %s, required: %s", 4, 0D, 999999D)));
        ResponseEntity<Object> actualResponse = operationController.takeMoney(new BaseRequest(4L, 999999D));
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("Test exception: put money to nonexistent user")
    public void testPutMoneyNonExistUser() {
        ResponseEntity<Object> expectedResponse = ResponseEntity.badRequest().body(new ErrorResponse(0, "User not found by id= -1"));
        ResponseEntity<Object> actualResponse = operationController.putMoney(new BaseRequest(-1L, 2000D));
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("Test exception: get operations by nonexistent user")
    public void testGetOperationsByNonExistUser() {
        ResponseEntity<Object> expectedResponse = ResponseEntity.badRequest().body(new ErrorResponse(0, "User not found by id= -1"));
        ResponseEntity<Object> actualResponse = operationController.getOperationList(new OperationListRequest(-1L, null, null));
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("Test exception: take money from nonexistent user")
    public void testTakeMoneyFromNonExistingUser() {
        ResponseEntity<Object> expectedResponse = ResponseEntity.badRequest().body(new ErrorResponse(0, "User not found by id= -1"));
        ResponseEntity<Object> actualResponse = operationController.takeMoney(new BaseRequest(-1L, 20D));
        assertEquals(expectedResponse, actualResponse);
    }

}