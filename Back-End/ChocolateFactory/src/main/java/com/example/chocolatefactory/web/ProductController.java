package com.example.chocolatefactory.web;

import com.example.chocolatefactory.domain.AppUserDetails;
import com.example.chocolatefactory.domain.responseDTOs.product.ProductDTO;
import com.example.chocolatefactory.domain.responseDTOs.product.ProductDetailsDTO;
import com.example.chocolatefactory.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductDTO>> allProducts() {
        List<ProductDTO> products = productService.getAllProducts();

        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailsDTO> getProduct(@PathVariable Long id, @AuthenticationPrincipal AppUserDetails appUserDetails) {
        ProductDetailsDTO productDetailsDTO = productService.getProductDetails(id, appUserDetails.getId());

        return ResponseEntity.ok(productDetailsDTO);
    }
}
