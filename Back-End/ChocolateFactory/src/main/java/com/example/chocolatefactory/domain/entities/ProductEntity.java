package com.example.chocolatefactory.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class ProductEntity extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String name;
    @Lob
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String imageUrl;
    @Column(nullable = false)
    private Integer quantity;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(nullable = false)
    private Boolean depleted;
    @Column(nullable = false, name = "low_quantity")
    private Boolean lowQuantity;
    @Column(nullable = false)
    private Boolean isDeleted;

    public String getName() {
        return name;
    }

    public ProductEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ProductEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ProductEntity setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public ProductEntity setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductEntity setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public Boolean getDepleted() {
        return depleted;
    }

    public ProductEntity setDepleted(Boolean depleted) {
        this.depleted = depleted;
        return this;
    }

    public Boolean getLowQuantity() {
        return lowQuantity;
    }

    public ProductEntity setLowQuantity(Boolean lowQuantity) {
        this.lowQuantity = lowQuantity;
        return this;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public ProductEntity setDeleted(Boolean deleted) {
        isDeleted = deleted;
        return this;
    }
}
