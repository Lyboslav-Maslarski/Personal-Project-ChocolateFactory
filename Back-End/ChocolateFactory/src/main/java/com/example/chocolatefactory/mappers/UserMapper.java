package com.example.chocolatefactory.mappers;

import com.example.chocolatefactory.domain.dtos.UserDTO;
import com.example.chocolatefactory.domain.entities.UserEntity;
import com.example.chocolatefactory.domain.records.RegisterDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toUserDTO(UserEntity userEntity);

    @Mapping(target = "password", ignore = true)
    UserEntity registerDTOToUserEntity(RegisterDTO registerDTO);
}
