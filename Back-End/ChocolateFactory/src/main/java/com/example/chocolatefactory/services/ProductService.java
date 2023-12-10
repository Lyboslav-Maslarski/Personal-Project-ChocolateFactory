package com.example.chocolatefactory.services;

import com.example.chocolatefactory.domain.requestDTOs.product.ProductAddDTO;
import com.example.chocolatefactory.domain.requestDTOs.product.ProductUpdateDTO;
import com.example.chocolatefactory.domain.responseDTOs.product.ProductDTO;
import com.example.chocolatefactory.domain.responseDTOs.product.ProductDetailsDTO;
import com.example.chocolatefactory.domain.responseDTOs.product.ProductUpdateDetailsDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getAllProducts();

    ProductDetailsDTO getProductDetails(Long id, Long appUserDetailsId);

    ProductDTO addProduct(ProductAddDTO productAddDTO);

    void deleteProduct(Long id);

    ProductUpdateDetailsDTO getProductForUpdate(Long id);

    void updateProduct(Long id, ProductUpdateDTO productUpdateDTO);
}
