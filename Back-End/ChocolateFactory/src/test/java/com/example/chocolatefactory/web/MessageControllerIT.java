package com.example.chocolatefactory.web;

import com.example.chocolatefactory.domain.requestDTOs.message.MessageAddDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MessageControllerIT {
    public static final String TITLE = "Test title";
    public static final String EMAIL = "example@example.com";
    public static final String CONTENT = "Some content";
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    public void testAddMessage() throws Exception {
        MessageAddDTO messageAddDTO = new MessageAddDTO(TITLE, EMAIL, CONTENT);

        mockMvc.perform(post("/api/messages/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(messageAddDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value(TITLE))
                .andExpect(jsonPath("$.contact").value(EMAIL))
                .andExpect(jsonPath("$.content").value(CONTENT));

    }

    @Test
    @WithMockUser
    public void testAddMessage_InvalidData() throws Exception {
        MessageAddDTO invalidMessageAddDTO = new MessageAddDTO("", EMAIL, CONTENT);

        mockMvc.perform(post("/api/messages/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidMessageAddDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid message data!"));
    }

    @Test
    @WithMockUser(roles = "MODERATOR")
    public void testChangeMessageStatus() throws Exception {
        long messageId = 1L;

        mockMvc.perform(patch("/api/messages/{id}", messageId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

    @Test
    @WithMockUser(roles = "MODERATOR")
    public void testGetAllMessages() throws Exception {

        mockMvc.perform(get("/api/messages/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(2))
                .andExpect(jsonPath("$[0].content").exists());
    }
}