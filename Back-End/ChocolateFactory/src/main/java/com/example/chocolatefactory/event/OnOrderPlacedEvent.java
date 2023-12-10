package com.example.chocolatefactory.event;

import org.springframework.context.ApplicationEvent;

import java.math.BigDecimal;

public class OnOrderPlacedEvent extends ApplicationEvent {
    private final Long buyerId;
    private final BigDecimal total;

    public OnOrderPlacedEvent(Object source, Long buyerId, BigDecimal total) {
        super(source);
        this.buyerId = buyerId;
        this.total = total;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public BigDecimal getTotal() {
        return total;
    }
}
