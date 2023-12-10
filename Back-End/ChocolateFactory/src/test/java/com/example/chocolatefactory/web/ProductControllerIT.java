package com.example.chocolatefactory.web;

import com.example.chocolatefactory.domain.requestDTOs.product.ProductAddDTO;
import com.example.chocolatefactory.domain.requestDTOs.product.ProductUpdateDTO;
import com.example.chocolatefactory.domain.responseDTOs.product.ProductDTO;
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

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testAllProducts() throws Exception {
        ResultActions result = mockMvc.perform(get("/api/products/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        String jsonResponse = result.andReturn().getResponse().getContentAsString();
        List<ProductDTO> products = objectMapper.readValue(jsonResponse, new TypeReference<>() {
        });

        assertEquals(4, products.size());
        assertTrue(products.stream().allMatch(p -> p.getId() != null));
    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName = "test")
    public void testGetProductDetails() throws Exception {
        long productId = 1L;

        mockMvc.perform(get("/api/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(productId))
                .andExpect(jsonPath("$.name").value("2 bons"));
    }

    @Test
    @WithMockUser(roles = {"MODERATOR"})
    public void testAddProduct() throws Exception {
        ProductAddDTO productAddDTO = new ProductAddDTO("Example", "Example",
                "Example", 10, BigDecimal.TEN);

        mockMvc.perform(post("/api/products/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productAddDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(5))
                .andExpect(jsonPath("$.name").value("Example"))
                .andExpect(jsonPath("$.price").value(10));
    }

    @Test
    @WithMockUser(roles = {"MODERATOR"})
    public void testAddProduct_InvalidData() throws Exception {
        ProductAddDTO invalidProductAddDTO = new ProductAddDTO("", "Example",
                "Example", 10, BigDecimal.TEN);

        mockMvc.perform(post("/api/products/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidProductAddDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid product data!"));
    }

    @Test
    @WithMockUser(roles = {"MODERATOR"})
    public void testDeleteProduct() throws Exception {
        long productId = 1L;

        mockMvc.perform(delete("/api/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = {"MODERATOR"})
    public void testGetProductForUpdate() throws Exception {
        long productId = 1L;

        mockMvc.perform(get("/api/products/update/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(productId));
    }

    @Test
    @WithMockUser(roles = {"MODERATOR"})
    public void testUpdateProduct() throws Exception {
        long productId = 1L;
        ProductUpdateDTO productUpdateDTO = new ProductUpdateDTO("New name", "Description",
                10, BigDecimal.TEN);

        mockMvc.perform(patch("/api/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productUpdateDTO)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"MODERATOR"})
    public void testUpdateProduct_InvalidData() throws Exception {
        long productId = 1L;
        ProductUpdateDTO invalidProductUpdateDTO = new ProductUpdateDTO("", "Description",
                10, BigDecimal.TEN);

        mockMvc.perform(patch("/api/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidProductUpdateDTO)))
                .andExpect(status().isBadRequest());
    }
}