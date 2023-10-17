package com.example.chocolatefactory.mappers;

import com.example.chocolatefactory.domain.entities.RoleEntity;
import com.example.chocolatefactory.domain.entities.UserEntity;
import com.example.chocolatefactory.domain.enums.RoleEnum;
import com.example.chocolatefactory.domain.requestDTOs.user.RegisterReqDTO;
import com.example.chocolatefactory.domain.responseDTOs.user.UserDTO;
import com.example.chocolatefactory.domain.responseDTOs.user.UserDetailsDTO;
import com.example.chocolatefactory.domain.responseDTOs.user.UserShorDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "roles", expression = "java(getEnumValue(userEntity))")
    UserDTO toUserDTO(UserEntity userEntity);

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    UserEntity registerDTOToUserEntity(RegisterReqDTO registerReqDTO);

    @Mapping(target = "moderator", expression = "java(isModerator(userEntity))")
    UserShorDTO toUserShortDTO(UserEntity userEntity);
    @Mapping(target = "orders", ignore = true)
    UserDetailsDTO toUserDetailsDTO(UserEntity userEntity);

    default Set<String> getEnumValue(UserEntity userEntity) {
        return userEntity.getRoles().stream().map(RoleEntity::getRole).map(RoleEnum::name).collect(Collectors.toSet());
    }

    default boolean isModerator(UserEntity userEntity) {
        return userEntity.getRoles().stream().anyMatch(r -> r.getRole().name().equals("ROLE_MODERATOR"));
    }
}
