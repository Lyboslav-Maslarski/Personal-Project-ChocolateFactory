package com.example.chocolatefactory.web;

import com.example.chocolatefactory.services.MessageService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

}
