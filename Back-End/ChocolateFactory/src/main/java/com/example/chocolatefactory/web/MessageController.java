package com.example.chocolatefactory.web;

import com.example.chocolatefactory.domain.requestDTOs.message.MessageAddDTO;
import com.example.chocolatefactory.domain.responseDTOs.error.ErrorDTO;
import com.example.chocolatefactory.domain.responseDTOs.message.MessageDTO;
import com.example.chocolatefactory.services.impl.MessageServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    private final MessageServiceImpl messageServiceImpl;

    public MessageController(MessageServiceImpl messageServiceImpl) {
        this.messageServiceImpl = messageServiceImpl;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addComment(@Valid @RequestBody MessageAddDTO messageAddDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new ErrorDTO("Invalid message data!"));
        }

        MessageDTO messageDTO = messageServiceImpl.saveMessage(messageAddDTO);

        return ResponseEntity.created(URI.create("api/messages/" + messageDTO.getId())).body(messageDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> changeMessageStatus(@PathVariable Long id) {
        messageServiceImpl.changeMessageStatus(id);

        return ResponseEntity.accepted().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<MessageDTO>> getAllMessages(){
        List<MessageDTO> messages = messageServiceImpl.getAllMessages();

        return ResponseEntity.ok(messages);
    }
}
