package com.example.chocolatefactory.services;

import com.example.chocolatefactory.domain.requestDTOs.message.MessageAddDTO;
import com.example.chocolatefactory.domain.responseDTOs.message.MessageDTO;

import java.util.List;

public interface MessageService {
    MessageDTO saveMessage(MessageAddDTO messageAddDTO);

    void changeMessageStatus(Long id);

    List<MessageDTO> getAllMessages();
}
