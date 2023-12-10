package com.example.chocolatefactory.web;

import com.example.chocolatefactory.domain.AppUserDetails;
import com.example.chocolatefactory.domain.requestDTOs.product.ProductAddDTO;
import com.example.chocolatefactory.domain.requestDTOs.product.ProductUpdateDTO;
import com.example.chocolatefactory.domain.responseDTOs.error.ErrorDTO;
import com.example.chocolatefactory.domain.responseDTOs.product.ProductDTO;
import com.example.chocolatefactory.domain.responseDTOs.product.ProductDetailsDTO;
import com.example.chocolatefactory.domain.responseDTOs.product.ProductUpdateDetailsDTO;
import com.example.chocolatefactory.exceptions.AppException;
import com.example.chocolatefactory.services.impl.ProductServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductServiceImpl productServiceImpl;

    public ProductController(ProductServiceImpl productServiceImpl) {
        this.productServiceImpl = productServiceImpl;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductDTO>> allProducts() {
        List<ProductDTO> products = productServiceImpl.getAllProducts();

        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailsDTO> getProductDetails(@PathVariable Long id, @AuthenticationPrincipal AppUserDetails appUserDetails) {
        ProductDetailsDTO productDetailsDTO = productServiceImpl.getProductDetails(id, appUserDetails.getId());

        return ResponseEntity.ok(productDetailsDTO);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@Valid @RequestBody ProductAddDTO productAddDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new AppException("Invalid product data!", HttpStatus.BAD_REQUEST);
        }
        ProductDTO productDTO = productServiceImpl.addProduct(productAddDTO);

        return ResponseEntity.created(URI.create("api/products/" + productDTO.getId())).body(productDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productServiceImpl.deleteProduct(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("update/{id}")
    public ResponseEntity<ProductUpdateDetailsDTO> getProductForUpdate(@PathVariable Long id) {
        ProductUpdateDetailsDTO productUpdateDetailsDTO = productServiceImpl.getProductForUpdate(id);

        return ResponseEntity.ok(productUpdateDetailsDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductUpdateDTO productUpdateDTO,
                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new ErrorDTO("Invalid product data!"));
        }

        productServiceImpl.updateProduct(id, productUpdateDTO);

        return ResponseEntity.ok().build();
    }
}
