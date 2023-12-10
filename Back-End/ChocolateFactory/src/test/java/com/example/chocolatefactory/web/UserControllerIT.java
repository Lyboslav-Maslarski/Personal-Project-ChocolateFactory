package com.example.chocolatefactory.web;

import com.example.chocolatefactory.domain.requestDTOs.user.LoginReqDTO;
import com.example.chocolatefactory.domain.requestDTOs.user.PasswordDTO;
import com.example.chocolatefactory.domain.requestDTOs.user.RegisterReqDTO;
import com.example.chocolatefactory.domain.requestDTOs.user.UserUpdateDTO;
import com.example.chocolatefactory.domain.responseDTOs.user.UserDetailsDTO;
import com.example.chocolatefactory.domain.responseDTOs.user.UserShorDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerIT {
    public static final String USER_EMAIL = "user@gmail.com";
    public static final String NEW_EMAIL = "new@gmail.com";
    public static final String PASSWORD = "123456";
    public static final String FULL_NAME = "Example Example";
    public static final String CITY = "Example";
    public static final String ADDRESS = "Example Address";
    public static final String PHONE = "0 888 888 888";
    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testLoginEndpoint() throws Exception {
        LoginReqDTO loginReqDTO = new LoginReqDTO(USER_EMAIL, PASSWORD.toCharArray());

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginReqDTO)))
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.email").value(USER_EMAIL))
                .andExpect(jsonPath("$.roles[0]").value("ROLE_USER"));
    }

    @Test
    public void testInvalidLoginCredentials() throws Exception {
        LoginReqDTO loginReqDTO = new LoginReqDTO("", "wrongPassword".toCharArray());

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginReqDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid login request data!"));
    }

    @Test
    public void testUnknownUser() throws Exception {
        LoginReqDTO loginReqDTO = new LoginReqDTO("unknownUser", "wrongPassword".toCharArray());

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginReqDTO)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Unknown user!"));
    }

    @Test
    public void testWrongPassword() throws Exception {
        LoginReqDTO loginReqDTO = new LoginReqDTO(USER_EMAIL, "wrongPassword".toCharArray());

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginReqDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid password!"));
    }

    @Test
    public void testRegisterEndpoint() throws Exception {
        RegisterReqDTO registerReqDTO = new RegisterReqDTO(NEW_EMAIL, PASSWORD.toCharArray(), FULL_NAME, CITY, ADDRESS, PHONE);

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerReqDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value(NEW_EMAIL))
                .andExpect(jsonPath("$.roles[0]").value("ROLE_USER"));
    }

    @Test
    public void testInvalidRegistrationData() throws Exception {
        RegisterReqDTO registerReqDTO = new RegisterReqDTO("", PASSWORD.toCharArray(), FULL_NAME, CITY, ADDRESS, PHONE);


        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerReqDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid user register data!"));
    }

    @Test
    public void testWithOccupiedEmail() throws Exception {
        RegisterReqDTO registerReqDTO = new RegisterReqDTO(USER_EMAIL, PASSWORD.toCharArray(), FULL_NAME, CITY, ADDRESS, PHONE);


        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerReqDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Email already exists!"));
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN"})
    public void testGetAllUsers() throws Exception {
        ResultActions result = mockMvc.perform(get("/api/users/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        String jsonResponse = result.andReturn().getResponse().getContentAsString();
        List<UserShorDTO> users = objectMapper.readValue(jsonResponse, new TypeReference<>() {
        });

        assertEquals(2, users.size());
        assertTrue(users.stream().allMatch(user -> user.getEmail() != null));
    }

    @Test
    @WithMockUser
    public void testGetUserById() throws Exception {
        long userId = 1L;

        ResultActions result = mockMvc.perform(get("/api/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        String jsonResponse = result.andReturn().getResponse().getContentAsString();
        UserDetailsDTO userDetails = objectMapper.readValue(jsonResponse, UserDetailsDTO.class);

        assertEquals(USER_EMAIL, userDetails.getEmail());
        assertEquals(1, userDetails.getId());
    }

    @Test
    @WithMockUser
    public void testUpdateUser() throws Exception {
        long userId = 1L;

        String fullName = "FULL_NAME";
        UserUpdateDTO updateDTO = new UserUpdateDTO(USER_EMAIL, fullName, CITY, ADDRESS, PHONE);

        mockMvc.perform(patch("/api/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value(fullName));
    }

    @Test
    @WithMockUser
    public void testUpdateUserWithInvalidData() throws Exception {
        long userId = 1L;

        UserUpdateDTO updateDTO = new UserUpdateDTO(USER_EMAIL, "", CITY, ADDRESS, PHONE);

        mockMvc.perform(patch("/api/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid user data!"));
    }

    @Test
    @WithMockUser
    public void testChangePassword() throws Exception {
        long userId = 1L;

        PasswordDTO passwordDTO = new PasswordDTO(PASSWORD, PASSWORD + 1);

        mockMvc.perform(patch("/api/users/{id}/password", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(passwordDTO)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void testChangePasswordWithInvalidData() throws Exception {
        long userId = 1L;

        PasswordDTO passwordDTO = new PasswordDTO("", PASSWORD);

        mockMvc.perform(patch("/api/users/{id}/password", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(passwordDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid user data!"));
    }

    @Test
    @WithMockUser
    public void testChangePasswordWithWrongPassword() throws Exception {
        long userId = 1L;

        PasswordDTO passwordDTO = new PasswordDTO("wrong password", PASSWORD);

        mockMvc.perform(patch("/api/users/{id}/password", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(passwordDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid password!"));
    }

    @Test
    @WithMockUser
    public void testPromoteUser() throws Exception {
        long userId = 1L;

        mockMvc.perform(patch("/api/users/{id}/promote", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void testDemoteUser() throws Exception {
        long userId = 1L;

        mockMvc.perform(patch("/api/users/{id}/demote", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void testDeleteUser() throws Exception {
        long userId = 1L;

        mockMvc.perform(delete("/api/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}