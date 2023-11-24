package com.example.chocolatefactory.web;

import com.example.chocolatefactory.domain.AppUserDetails;
import com.example.chocolatefactory.domain.requestDTOs.comment.CommentAddDTO;
import com.example.chocolatefactory.domain.requestDTOs.message.MessageAddDTO;
import com.example.chocolatefactory.domain.responseDTOs.comment.CommentDTO;
import com.example.chocolatefactory.domain.responseDTOs.error.ErrorDTO;
import com.example.chocolatefactory.domain.responseDTOs.message.MessageDTO;
import com.example.chocolatefactory.services.MessageService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addComment(@Valid @RequestBody MessageAddDTO messageAddDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new ErrorDTO("Invalid message data!"));
        }

        MessageDTO messageDTO = messageService.saveMessage(messageAddDTO);

        return ResponseEntity.created(URI.create("api/messages/" + messageDTO.getId())).body(messageDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> changeMessageStatus(@PathVariable Long id) {
        messageService.changeMessageStatus(id);

        return ResponseEntity.accepted().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<MessageDTO>> getAllMessages(){
        List<MessageDTO> messages = messageService.getAllMessages();

        return ResponseEntity.ok(messages);
    }
}
