package com.example.chocolatefactory.web;

import com.example.chocolatefactory.domain.requestDTOs.comment.CommentAddDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CommentControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithUserDetails(userDetailsServiceBeanName = "test")
    public void testAddComment() throws Exception {
        String text = "Some text";
        CommentAddDTO commentAddDTO = new CommentAddDTO(text, 1L);

        mockMvc.perform(post("/api/comments/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commentAddDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.text").value(text));
    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName = "test")
    public void testAddComment_InvalidData() throws Exception {
        CommentAddDTO invalidCommentAddDTO = new CommentAddDTO("", 1L);

        mockMvc.perform(post("/api/comments/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidCommentAddDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid comment request data!"));
    }

    @Test
    @WithMockUser
    public void testDeleteComment() throws Exception {
        long commentId = 1L;

         mockMvc.perform(delete("/api/comments/{id}", commentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}