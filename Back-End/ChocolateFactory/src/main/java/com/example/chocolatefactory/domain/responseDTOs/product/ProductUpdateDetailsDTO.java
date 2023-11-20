package com.example.chocolatefactory.domain.responseDTOs.product;

import java.math.BigDecimal;

public class ProductUpdateDetailsDTO {
    private Long id;
    private String name;
    private String description;
    private Integer quantity;
    private BigDecimal price;

    public Long getId() {
        return id;
    }

    public ProductUpdateDetailsDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProductUpdateDetailsDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ProductUpdateDetailsDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public ProductUpdateDetailsDTO setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductUpdateDetailsDTO setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }
}
