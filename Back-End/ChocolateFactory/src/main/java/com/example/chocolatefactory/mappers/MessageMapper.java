package com.example.chocolatefactory.mappers;

import com.example.chocolatefactory.domain.entities.MessageEntity;
import com.example.chocolatefactory.domain.requestDTOs.message.MessageAddDTO;
import com.example.chocolatefactory.domain.responseDTOs.message.MessageDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    MessageEntity messageAddDtoToEntity(MessageAddDTO messageAddDTO);

    MessageDTO entityToMessageDto(MessageEntity saved);
}
