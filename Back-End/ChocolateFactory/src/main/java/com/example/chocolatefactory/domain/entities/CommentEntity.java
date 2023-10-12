package com.example.chocolatefactory.domain.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "comments")
public class CommentEntity extends BaseEntity {
    @Column(nullable = false, columnDefinition = "TEXT")
    private String text;
    @ManyToOne
    private ProductEntity product;
    @ManyToOne
    private UserEntity user;

    public String getText() {
        return text;
    }

    public CommentEntity setText(String text) {
        this.text = text;
        return this;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public CommentEntity setProduct(ProductEntity product) {
        this.product = product;
        return this;
    }

    public UserEntity getUser() {
        return user;
    }

    public CommentEntity setUser(UserEntity reviewer) {
        this.user = reviewer;
        return this;
    }
}
