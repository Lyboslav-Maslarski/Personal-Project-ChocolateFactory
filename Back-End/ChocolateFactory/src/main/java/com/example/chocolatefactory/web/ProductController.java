package com.example.chocolatefactory.web;

import com.example.chocolatefactory.services.ProductService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
}
