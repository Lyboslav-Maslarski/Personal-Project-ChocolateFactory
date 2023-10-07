package com.example.chocolatefactory.mappers;

import com.example.chocolatefactory.domain.entities.ProductEntity;
import com.example.chocolatefactory.domain.requestDTOs.product.ProductAddDTO;
import com.example.chocolatefactory.domain.responseDTOs.product.ProductDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductEntity productDtoToEntity(ProductAddDTO productAddDTO);
    ProductDTO entityToProductDTO(ProductEntity productEntity);
}
