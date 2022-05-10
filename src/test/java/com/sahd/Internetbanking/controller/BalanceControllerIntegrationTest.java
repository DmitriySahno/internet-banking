package com.sahd.Internetbanking.controller;

import com.sahd.Internetbanking.payload.response.BalanceResponse;
import com.sahd.Internetbanking.payload.response.ErrorResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class BalanceControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Test get balance by existing user")
    public void testGetBalance() throws Exception {
        this.mockMvc.perform(get("/getBalance/1")).andDo(print()).andExpect(status().isOk()).
                andExpect(content().json("{\"value\": 120.0}"));
    }

    @Test
    @DisplayName("Test get balance by nonexistent user")
    public void testGetBalanceByNonExistUser() throws Exception {
        this.mockMvc.perform(get("/getBalance/-1")).andDo(print()).andExpect(status().isBadRequest()).
                andExpect(content().json("{\"value\": -1,\"message\": \"User not found by id= -1\"}"));
    }

}