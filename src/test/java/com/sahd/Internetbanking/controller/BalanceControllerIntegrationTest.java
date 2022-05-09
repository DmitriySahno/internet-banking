package com.sahd.Internetbanking.controller;

import com.sahd.Internetbanking.payload.response.BalanceResponse;
import com.sahd.Internetbanking.payload.response.ErrorResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class BalanceControllerIntegrationTest {

    @Autowired
    private BalanceController balanceController;

    @Test
    @DisplayName("Test get balance by existing user")
    public void testGetBalances() {
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok(new BalanceResponse(120D));
        ResponseEntity<Object> actualResponse = balanceController.getBalance(1L);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("Test exception: get balance by nonexistent user")
    public void testGetBalancesByNonExistentUser() {
        ResponseEntity<Object> expectedResponse = ResponseEntity.badRequest().body(new ErrorResponse(-1, "User not found by id= -1"));
        ResponseEntity<Object> actualResponse = balanceController.getBalance(-1L);
        assertEquals(expectedResponse, actualResponse);
    }
}