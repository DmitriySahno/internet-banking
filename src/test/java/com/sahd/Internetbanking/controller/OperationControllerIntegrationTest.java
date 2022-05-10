package com.sahd.Internetbanking.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class OperationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Test take money")
    public void testTakeMoney() throws Exception {
        this.mockMvc.perform(post("/takeMoney").contentType(MediaType.APPLICATION_JSON)
                .content("{\"userId\":1,\"amount\":20}")).andExpect(status().isOk())
                .andExpect(content().json("{\"value\": 1.0}"));
    }

    @Test
    @DisplayName("Test put money")
    public void testPutMoney() throws Exception {
        this.mockMvc.perform(post("/putMoney").contentType(MediaType.APPLICATION_JSON)
                .content("{\"userId\":1,\"amount\":20}")).andExpect(status().isOk())
                .andExpect(content().json("{\"value\": 1.0}"));
    }


    @Test
    @DisplayName("Test transfer money")
    public void testTransferMoney() throws Exception {
        this.mockMvc.perform(post("/transferMoney").contentType(MediaType.APPLICATION_JSON)
                .content("{\"userFromId\":1,\"userToId\":1,\"amount\":20}")).andExpect(status().isOk())
                .andExpect(content().json("{\"value\": 1.0}"));
    }

    @Test
    @DisplayName("Test get operations with both dates")
    public void testGetOperationsWithBothDates() throws Exception {
        this.mockMvc.perform(get("/getOperationList").contentType(MediaType.APPLICATION_JSON)
                .content("{\"userId\":1,\"dateFrom\":\"2022-01-01T10:00:00\",\"dateTo\":\"2022-01-01T10:59:59\"}")).andExpect(status().isOk())
                .andExpect(content().json("[{\"id\": 1,\"user\": {\"id\": 1,\"name\": \"John Malkovich\"},\"type\": \"DEPOSIT\",\"amount\": 100.0,\"date\": \"2022-01-01 10:00:00\"}]"));
    }

    @Test
    @DisplayName("Test get operations with start date")
    public void testGetOperationsWithStartDate() throws Exception {
        this.mockMvc.perform(get("/getOperationList").contentType(MediaType.APPLICATION_JSON)
                .content("{\"userId\":1,\"dateFrom\":\"2022-01-01T11:00:00\",\"dateTo\":\"\"}")).andExpect(status().isOk())
                .andExpect(content().json("[{\"id\": 2,\"user\": {\"id\": 1,\"name\": \"John Malkovich\"},\"type\": \"DEPOSIT\",\"amount\": 20.0,\"date\": \"2022-01-01 12:00:00\"}]"));
    }

    @Test
    @DisplayName("Test get operations with end date")
    public void testGetOperationsWithEndDate() throws Exception {
        this.mockMvc.perform(get("/getOperationList").contentType(MediaType.APPLICATION_JSON)
                .content("{\"userId\":1,\"dateFrom\":\"\",\"dateTo\":\"2022-01-01T11:00:00\"}")).andExpect(status().isOk())
                .andExpect(content().json("[{\"id\": 1,\"user\": {\"id\": 1,\"name\": \"John Malkovich\"},\"type\": \"DEPOSIT\",\"amount\": 100.0,\"date\": \"2022-01-01 10:00:00\"}]"));
    }

    @Test
    @DisplayName("Test get operations w/o dates")
    public void testGetOperations() throws Exception {
        this.mockMvc.perform(get("/getOperationList").contentType(MediaType.APPLICATION_JSON)
                .content("{\"userId\":1,\"dateFrom\":\"\",\"dateTo\":\"\"}")).andExpect(status().isOk())
                .andExpect(content().json("[{\"id\": 1,\"user\": {\"id\": 1,\"name\": \"John Malkovich\"},\"type\": \"DEPOSIT\",\"amount\": 100.0,\"date\": \"2022-01-01 10:00:00\"}," +
                        "{\"id\": 2,\"user\": {\"id\": 1,\"name\": \"John Malkovich\"},\"type\": \"DEPOSIT\",\"amount\": 20.0,\"date\": \"2022-01-01 12:00:00\"}]"));
    }

    @Test
    @DisplayName("Test get operations by user w/o operations")
    public void testGetOperationsByUserWithoutOperations() throws Exception {
        this.mockMvc.perform(get("/getOperationList").contentType(MediaType.APPLICATION_JSON)
                .content("{\"userId\":3,\"dateFrom\":\"\",\"dateTo\":\"2022-01-01T11:00:00\"}")).andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    @DisplayName("Test exception: take non existing money")
    public void testTakeNonExistMoney() throws Exception {
        this.mockMvc.perform(post("/takeMoney").contentType(MediaType.APPLICATION_JSON)
                .content("{\"userId\":4,\"amount\":\"999999\"}")).andExpect(status().isBadRequest())
                .andExpect(content().json("{\"value\": 0,\"message\": \"Not enough facilities on Balance of user: 4, balance: 0.0, required: 999999.0\"}"));
    }

    @Test
    @DisplayName("Test exception: put money to nonexistent user")
    public void testPutMoneyNonExistUser() throws Exception {
        this.mockMvc.perform(post("/putMoney").contentType(MediaType.APPLICATION_JSON)
                .content("{\"userId\":-1,\"amount\":\"0\"}")).andExpect(status().isBadRequest())
                .andExpect(content().json("{\"value\": 0,\"message\": \"User not found by id= -1\"}"));
    }

    @Test
    @DisplayName("Test exception: get operations by nonexistent user")
    public void testGetOperationsByNonExistUser() throws Exception {
        this.mockMvc.perform(get("/getOperationList").contentType(MediaType.APPLICATION_JSON)
                .content("{\"userId\":-1,\"amount\":\"0\"}")).andExpect(status().isBadRequest())
                .andExpect(content().json("{\"value\": 0,\"message\": \"User not found by id= -1\"}"));
    }

    @Test
    @DisplayName("Test exception: take money from nonexistent user")
    public void testTakeMoneyFromNonExistingUser() throws Exception {
        this.mockMvc.perform(post("/takeMoney").contentType(MediaType.APPLICATION_JSON)
                .content("{\"userId\":-1,\"amount\":\"0\"}")).andExpect(status().isBadRequest())
                .andExpect(content().json("{\"value\": 0,\"message\": \"User not found by id= -1\"}"));
    }

    @Test
    @DisplayName("Test exception: transfer money from nonexistent user")
    public void testTransferMoneyFromNonExistingUser() throws Exception {
        this.mockMvc.perform(post("/transferMoney").contentType(MediaType.APPLICATION_JSON)
                .content("{\"userFromId\":-1,\"userToId\":-1,\"amount\":\"0\"}")).andExpect(status().isBadRequest())
                .andExpect(content().json("{\"value\": 0,\"message\": \"User not found by id= -1\"}"));
    }

}