package com.example.chocolatefactory.mappers;

import com.example.chocolatefactory.domain.entities.CommentEntity;
import com.example.chocolatefactory.domain.requestDTOs.comment.CommentAddDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    CommentEntity commentDTOToEntity(CommentAddDTO commentAddDTO);
}
