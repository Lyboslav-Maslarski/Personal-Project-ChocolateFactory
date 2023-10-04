package com.example.chocolatefactory.mappers;

import com.example.chocolatefactory.domain.entities.CommentEntity;
import com.example.chocolatefactory.domain.requestDTOs.comment.CommentAddDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(target = "productId", ignore = true)
    CommentEntity commentDTOToEntity(CommentAddDTO commentAddDTO);
}
