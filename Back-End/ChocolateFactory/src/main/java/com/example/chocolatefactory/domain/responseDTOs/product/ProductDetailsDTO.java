package com.example.chocolatefactory.domain.responseDTOs.product;

import com.example.chocolatefactory.domain.responseDTOs.comment.CommentDTO;

import java.math.BigDecimal;
import java.util.List;

public class ProductDetailsDTO {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private BigDecimal price;
    private List<CommentDTO> comments;

    public Long getId() {
        return id;
    }

    public ProductDetailsDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProductDetailsDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ProductDetailsDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ProductDetailsDTO setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductDetailsDTO setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public List<CommentDTO> getComments() {
        return comments;
    }

    public ProductDetailsDTO setComments(List<CommentDTO> comments) {
        this.comments = comments;
        return this;
    }
}
