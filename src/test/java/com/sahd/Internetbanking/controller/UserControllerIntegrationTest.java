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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Test getting all users")
    void testGetAll() throws Exception {
        this.mockMvc.perform(get("/users")).andExpect(status().isOk())
                .andExpect(content().json("[{\"id\": 1,\"name\": \"John Malkovich\"},{\"id\": 2,\"name\": \"Paul McKinley\"\n" +
                        "},{\"id\": 3,\"name\": \"Christopher Robertson\"},{\"id\": 4,\"name\": \"Edvard Schwarts\"}]"));
    }

    @Test
    @DisplayName("Test add user")
    void testAddUser() throws Exception {
        this.mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content("{\"id\":0, \"name\":\"Viktorija Secret\"}")).andExpect(status().isOk())
                .andExpect(content().json("{\"id\":5,\"name\":\"Viktorija Secret\"}"));
    }

    @Test
    @DisplayName("Test get user and get nonexistent user")
    void testGetUser() throws Exception {
        this.mockMvc.perform(get("/users/1")).andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"name\":\"John Malkovich\"}"));

        this.mockMvc.perform(get("/users/-1")).andExpect(status().isBadRequest())
                .andExpect(content().json("{\"value\":0,\"message\":\"User not found by id= -1\"}"));
    }

    @Test
    @DisplayName("Test update user and update nonexistent user")
    void testUpdateUser() throws Exception {
        this.mockMvc.perform(put("/users").contentType(MediaType.APPLICATION_JSON).content("{\"id\":1, \"name\":\"New name\"}")).andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"name\":\"New name\"}"));

        this.mockMvc.perform(put("/users").contentType(MediaType.APPLICATION_JSON).content("{\"id\":-1, \"name\":\"\"}")).andExpect(status().isBadRequest())
                .andExpect(content().json("{\"value\":0,\"message\":\"User not found by id= -1\"}"));
    }

    @Test
    @DisplayName("Test delete user and delete nonexistent user")
    void testDeleteUserAndCheckException() throws Exception {
        this.mockMvc.perform(delete("/users/4")).andExpect(status().isOk())
                .andExpect(content().string(""));

        this.mockMvc.perform(get("/users/4")).andExpect(status().isBadRequest())
                .andExpect(content().string("{\"value\":0,\"message\":\"User not found by id= 4\"}"));
    }
}