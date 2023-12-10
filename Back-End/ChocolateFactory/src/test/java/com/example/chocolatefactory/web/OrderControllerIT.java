package com.example.chocolatefactory.web;

import com.example.chocolatefactory.domain.requestDTOs.order.OrderAddDTO;
import com.example.chocolatefactory.domain.requestDTOs.order.OrderIdDTO;
import com.example.chocolatefactory.domain.responseDTOs.order.OrderDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithUserDetails(userDetailsServiceBeanName = "test")
    public void testAddOrder() throws Exception {
        OrderAddDTO orderAddDTO = new OrderAddDTO(List.of(1L, 2L, 3L));

        mockMvc.perform(post("/api/orders/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderAddDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.orderNumber").exists())
                .andExpect(jsonPath("$.total").exists())
                .andExpect(jsonPath("$.status").value("WAITING"));
    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName = "test")
    public void testAddOrderWithEmptyList() throws Exception {
        OrderAddDTO orderAddDTO = new OrderAddDTO(List.of());

        mockMvc.perform(post("/api/orders/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderAddDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid order data!"));
    }

    @Test
    @WithMockUser
    public void testGetOrderDetails() throws Exception {
        String orderNumber = "5ec0bc77-1a81-493b-8197-b0af73ab565c";
        UUID uuid = UUID.fromString(orderNumber);

        mockMvc.perform(get("/api/orders/{orderNumber}", uuid)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.orderNumber").value(orderNumber))
                .andExpect(jsonPath("$.status").value("CANCELLED"));
    }

    @Test
    @WithMockUser
    public void testGetOrderDetailsWithNonExistingOrder() throws Exception {
        UUID orderNumber = UUID.randomUUID();

        mockMvc.perform(get("/api/orders/{orderNumber}", orderNumber)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Order not found!"));
    }

    @Test
    @WithMockUser
    public void testDeleteOrder() throws Exception {
        long orderId = 1L;
        mockMvc.perform(delete("/api/orders/{id}", orderId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = {"MODERATOR"})
    public void testGetAllOrders() throws Exception {
        ResultActions result = mockMvc.perform(get("/api/orders/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        String jsonResponse = result.andReturn().getResponse().getContentAsString();
        List<OrderDTO> orders = objectMapper.readValue(jsonResponse, new TypeReference<>() {
        });

        assertEquals(3, orders.size());
        assertTrue(orders.stream().allMatch(order -> order.getId() != null));
    }

    @Test
    @WithMockUser(roles = {"MODERATOR"})
    public void testAcceptOrder() throws Exception {
        OrderIdDTO orderIdDTO = new OrderIdDTO(1L);

        mockMvc.perform(patch("/api/orders/accept")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderIdDTO)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"MODERATOR"})
    public void testDispatchOrder() throws Exception {
        OrderIdDTO orderIdDTO = new OrderIdDTO(1L);

        mockMvc.perform(patch("/api/orders/dispatch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderIdDTO)))
                .andExpect(status().isOk());
    }
}