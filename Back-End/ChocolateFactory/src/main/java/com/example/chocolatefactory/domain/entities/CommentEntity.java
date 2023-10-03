package com.example.chocolatefactory.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "comments")
public class CommentEntity extends BaseEntity {
    @Column(nullable = false, columnDefinition = "TEXT")
    private String text;
    @ManyToOne
    private ProductEntity product;
    @ManyToOne
    private UserEntity reviewer;

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

    public UserEntity getReviewer() {
        return reviewer;
    }

    public CommentEntity setReviewer(UserEntity reviewer) {
        this.reviewer = reviewer;
        return this;
    }
}
