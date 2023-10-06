package com.example.chocolatefactory.services;

import com.example.chocolatefactory.domain.entities.ProductEntity;
import com.example.chocolatefactory.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


}
