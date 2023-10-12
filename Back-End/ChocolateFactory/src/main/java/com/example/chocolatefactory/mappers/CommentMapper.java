package com.example.chocolatefactory.mappers;

import com.example.chocolatefactory.domain.entities.CommentEntity;
import com.example.chocolatefactory.domain.entities.RoleEntity;
import com.example.chocolatefactory.domain.entities.UserEntity;
import com.example.chocolatefactory.domain.enums.RoleEnum;
import com.example.chocolatefactory.domain.requestDTOs.comment.CommentAddDTO;
import com.example.chocolatefactory.domain.responseDTOs.comment.CommentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    CommentEntity commentDTOToEntity(CommentAddDTO commentAddDTO);

    @Mapping(target = "user", expression = "java(getUserName(commentEntity))")
    CommentDTO commentEntityToCommentDTO(CommentEntity commentEntity);

    default String getUserName(CommentEntity commentEntity) {
        return commentEntity.getUser().getFullName();
    }
}
