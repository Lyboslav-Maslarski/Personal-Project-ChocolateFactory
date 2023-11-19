package com.example.chocolatefactory.services;

import com.example.chocolatefactory.domain.entities.CommentEntity;
import com.example.chocolatefactory.domain.entities.ProductEntity;
import com.example.chocolatefactory.domain.requestDTOs.product.ProductAddDTO;
import com.example.chocolatefactory.domain.responseDTOs.comment.CommentDTO;
import com.example.chocolatefactory.domain.responseDTOs.product.ProductDTO;
import com.example.chocolatefactory.domain.responseDTOs.product.ProductDetailsDTO;
import com.example.chocolatefactory.exceptions.AppException;
import com.example.chocolatefactory.mappers.CommentMapper;
import com.example.chocolatefactory.mappers.ProductMapper;
import com.example.chocolatefactory.repositories.CommentRepository;
import com.example.chocolatefactory.repositories.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CommentRepository commentRepository;
    private final ProductMapper productMapper;
    private final CommentMapper commentMapper;

    public ProductService(ProductRepository productRepository, CommentRepository commentRepository, ProductMapper productMapper, CommentMapper commentMapper) {
        this.productRepository = productRepository;
        this.commentRepository = commentRepository;
        this.productMapper = productMapper;
        this.commentMapper = commentMapper;
    }


    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::entityToProductDTO)
                .collect(Collectors.toList());
    }

    public ProductDetailsDTO getProductDetails(Long id, Long appUserDetailsId) {
        ProductEntity productEntity = productRepository.findById(id)
                .orElseThrow(() -> new AppException("Product with id " + id + "not found!", HttpStatus.NOT_FOUND));

        ProductDetailsDTO productDetailsDTO = productMapper.entityToProductDetailsDTO(productEntity);
        List<CommentEntity> commentEntities = commentRepository.findByProductId(productEntity.getId());

        List<CommentDTO> comments = commentEntities.stream().map(commentEntity -> {
            CommentDTO commentDTO = commentMapper.commentEntityToCommentDTO(commentEntity);
            commentDTO.setOwner(Objects.equals(commentEntity.getUser().getId(), appUserDetailsId));
            return commentDTO;
        }).toList();

        productDetailsDTO.setComments(comments);

        return productDetailsDTO;
    }

    public ProductDTO addProduct(ProductAddDTO productAddDTO) {
        ProductEntity productEntity = productMapper.productDtoToEntity(productAddDTO)
                .setDepleted(false)
                .setLowQuantity(false);

        ProductEntity saved = productRepository.save(productEntity);

        return productMapper.entityToProductDTO(saved);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
