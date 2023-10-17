package com.example.chocolatefactory.domain.responseDTOs.order;

import java.math.BigDecimal;
import java.util.UUID;

public class OrderDTO {
    private Long id;
    private UUID orderNumber;
    private BigDecimal total;
    private String status;

    public Long getId() {
        return id;
    }

    public OrderDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public UUID getOrderNumber() {
        return orderNumber;
    }

    public OrderDTO setOrderNumber(UUID orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public OrderDTO setTotal(BigDecimal total) {
        this.total = total;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public OrderDTO setStatus(String status) {
        this.status = status;
        return this;
    }
}
