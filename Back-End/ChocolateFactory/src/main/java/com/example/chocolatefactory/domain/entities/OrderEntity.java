package com.example.chocolatefactory.domain.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

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

    @Column(nullable = false)
    private Instant expected;

    @Column(nullable = false)
    private BigDecimal total;

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

    public Instant getExpected() {
        return expected;
    }

    public OrderEntity setExpected(Instant expected) {
        this.expected = expected;
        return this;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public OrderEntity setTotal(BigDecimal total) {
        this.total = total;
        return this;
    }
}
