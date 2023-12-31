package com.example.chocolatefactory.services.impl;

import com.example.chocolatefactory.domain.entities.MessageEntity;
import com.example.chocolatefactory.domain.enums.MessageStatus;
import com.example.chocolatefactory.domain.requestDTOs.message.MessageAddDTO;
import com.example.chocolatefactory.domain.responseDTOs.message.MessageDTO;
import com.example.chocolatefactory.exceptions.AppException;
import com.example.chocolatefactory.mappers.MessageMapper;
import com.example.chocolatefactory.repositories.MessageRepository;
import com.example.chocolatefactory.services.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;

    public MessageServiceImpl(MessageRepository messageRepository, MessageMapper messageMapper) {
        this.messageRepository = messageRepository;
        this.messageMapper = messageMapper;
    }

    @Override
    public MessageDTO saveMessage(MessageAddDTO messageAddDTO) {
        MessageEntity messageEntity = messageMapper.messageAddDtoToEntity(messageAddDTO);
        messageEntity.setStatus(MessageStatus.UNANSWERED);

        MessageEntity saved = messageRepository.save(messageEntity);

        return messageMapper.entityToMessageDto(saved);
    }

    @Override
    public void changeMessageStatus(Long id) {
        MessageEntity messageEntity = messageRepository.findById(id)
                .orElseThrow(() -> new AppException("Message with id " + id + " not found!", HttpStatus.NOT_FOUND));

        messageEntity.setStatus(MessageStatus.ANSWERED);

        messageRepository.save(messageEntity);
    }

    @Override
    public List<MessageDTO> getAllMessages() {
        return messageRepository.findAllByStatus(MessageStatus.UNANSWERED)
                .stream()
                .map(messageMapper::entityToMessageDto)
                .collect(Collectors.toList());
    }
}
