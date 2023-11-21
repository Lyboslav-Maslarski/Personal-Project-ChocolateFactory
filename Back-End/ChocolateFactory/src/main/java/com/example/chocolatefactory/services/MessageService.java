package com.example.chocolatefactory.services;

import com.example.chocolatefactory.mappers.MessageMapper;
import com.example.chocolatefactory.repositories.MessageRepository;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;

    public MessageService(MessageRepository messageRepository, MessageMapper messageMapper) {
        this.messageRepository = messageRepository;
        this.messageMapper = messageMapper;
    }

}
