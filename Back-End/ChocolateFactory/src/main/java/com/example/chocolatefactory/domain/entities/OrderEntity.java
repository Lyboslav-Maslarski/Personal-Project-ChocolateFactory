package com.example.chocolatefactory.domain.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class OrderEntity extends BaseEntity {
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "order_product",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<ProductEntity> products;
    @ManyToOne
    private UserEntity buyer;
    @Column(nullable = false, name = "order_number")
    private UUID orderNumber;
    @Column(nullable = false)
    private BigDecimal total;
    @Column(nullable = false)
    private Boolean approved;
    @Column(nullable = false)
    private Boolean dispatched;


    public List<ProductEntity> getProducts() {
        return products;
    }

    public OrderEntity setProducts(List<ProductEntity> products) {
        this.products = products;
        return this;
    }

    public UserEntity getBuyer() {
        return buyer;
    }

    public OrderEntity setBuyer(UserEntity buyer) {
        this.buyer = buyer;
        return this;
    }

    public UUID getOrderNumber() {
        return orderNumber;
    }

    public OrderEntity setOrderNumber(UUID orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public OrderEntity setTotal(BigDecimal total) {
        this.total = total;
        return this;
    }

    public Boolean getApproved() {
        return approved;
    }

    public OrderEntity setApproved(Boolean approved) {
        this.approved = approved;
        return this;
    }

    public Boolean getDispatched() {
        return dispatched;
    }

    public OrderEntity setDispatched(Boolean dispatched) {
        this.dispatched = dispatched;
        return this;
    }
}
