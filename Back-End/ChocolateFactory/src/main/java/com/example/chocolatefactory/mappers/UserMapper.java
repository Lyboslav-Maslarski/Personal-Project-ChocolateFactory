package com.example.chocolatefactory.mappers;

import com.example.chocolatefactory.domain.requestDTOs.user.UserReqDTO;
import com.example.chocolatefactory.domain.responseDTOs.user.UserDTO;
import com.example.chocolatefactory.domain.entities.UserEntity;
import com.example.chocolatefactory.domain.responseDTOs.user.UserShorDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toUserDTO(UserEntity userEntity);

    @Mapping(target = "password", ignore = true)
    UserEntity registerDTOToUserEntity(UserReqDTO userReqDTO);

    UserShorDTO toUserShortDTO(UserEntity userEntity);
}
