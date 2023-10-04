package com.example.chocolatefactory.domain.responseDTOs.order;

import com.example.chocolatefactory.domain.responseDTOs.product.ProductDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class OrderDetailsDTO {
    private Long id;
    private UUID orderNumber;
    private BigDecimal total;
    private Boolean approved;
    private Boolean dispatched;
    private List<ProductDTO> products;

    public Long getId() {
        return id;
    }

    public OrderDetailsDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public UUID getOrderNumber() {
        return orderNumber;
    }

    public OrderDetailsDTO setOrderNumber(UUID orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public OrderDetailsDTO setTotal(BigDecimal total) {
        this.total = total;
        return this;
    }

    public Boolean getApproved() {
        return approved;
    }

    public OrderDetailsDTO setApproved(Boolean approved) {
        this.approved = approved;
        return this;
    }

    public Boolean getDispatched() {
        return dispatched;
    }

    public OrderDetailsDTO setDispatched(Boolean dispatched) {
        this.dispatched = dispatched;
        return this;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public OrderDetailsDTO setProducts(List<ProductDTO> products) {
        this.products = products;
        return this;
    }
}
