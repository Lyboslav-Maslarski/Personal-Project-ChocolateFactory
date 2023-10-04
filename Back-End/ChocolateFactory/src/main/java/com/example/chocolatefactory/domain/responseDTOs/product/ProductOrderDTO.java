package com.example.chocolatefactory.domain.responseDTOs.product;

import java.math.BigDecimal;

public class ProductOrderDTO {
    private Long id;
    private String name;
    private BigDecimal price;

    public Long getId() {
        return id;
    }

    public ProductOrderDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProductOrderDTO setName(String name) {
        this.name = name;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductOrderDTO setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }
}
