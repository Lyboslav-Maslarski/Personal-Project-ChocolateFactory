package com.example.chocolatefactory.services;

import com.example.chocolatefactory.domain.entities.MessageEntity;
import com.example.chocolatefactory.domain.enums.MessageStatus;
import com.example.chocolatefactory.domain.requestDTOs.message.MessageAddDTO;
import com.example.chocolatefactory.domain.responseDTOs.message.MessageDTO;
import com.example.chocolatefactory.exceptions.AppException;
import com.example.chocolatefactory.mappers.MessageMapper;
import com.example.chocolatefactory.repositories.MessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class MessageServiceTest {
    public static final String TITLE = "title";
    public static final String EMAIL = "example@example.com";
    public static final String CONTENT = "Some content";
    @Mock
    private MessageRepository messageRepository;

    @Mock
    private MessageMapper messageMapper;

    @InjectMocks
    private MessageService messageService;

    @BeforeEach
    void setUp() {
        messageService = new MessageService(messageRepository, messageMapper);
    }

    @Test
    void testSaveMessage_ShouldSaveMessage() {
        MessageAddDTO messageAddDTO = new MessageAddDTO(TITLE, EMAIL, CONTENT);
        MessageEntity messageEntity = new MessageEntity();
        MessageDTO expected = new MessageDTO().setTitle(TITLE).setContact(EMAIL).setContent(CONTENT);

        when(messageMapper.messageAddDtoToEntity(messageAddDTO)).thenReturn(messageEntity);
        when(messageRepository.save(messageEntity)).thenReturn(messageEntity);
        when(messageMapper.entityToMessageDto(messageEntity)).thenReturn(expected);

        MessageDTO savedMessage = messageService.saveMessage(messageAddDTO);

        assertNotNull(savedMessage);
        assertEquals(expected.getTitle(), savedMessage.getTitle());
        assertEquals(expected.getContact(), savedMessage.getContact());
        assertEquals(expected.getContent(), savedMessage.getContent());
        assertEquals(MessageStatus.UNANSWERED, messageEntity.getStatus());

        verify(messageMapper, times(1)).messageAddDtoToEntity(messageAddDTO);
        verify(messageRepository, times(1)).save(messageEntity);
    }

    @Test
    void testChangeMessageStatus_ShouldChangeMessageStatus() {
        Long messageId = 1L;
        MessageEntity messageEntity = new MessageEntity(/* add necessary data */);
        when(messageRepository.findById(messageId)).thenReturn(Optional.of(messageEntity));
        when(messageRepository.save(messageEntity)).thenReturn(messageEntity);

        assertDoesNotThrow(() -> messageService.changeMessageStatus(messageId));

        assertEquals(MessageStatus.ANSWERED, messageEntity.getStatus());

        verify(messageRepository, times(1)).findById(messageId);
        verify(messageRepository, times(1)).save(messageEntity);
    }

    @Test
    void testChangeMessageStatus_WhenMessageNotFound_ShouldThrowException() {
        Long messageId = 1L;
        when(messageRepository.findById(messageId)).thenReturn(Optional.empty());

        assertThrows(AppException.class, () -> messageService.changeMessageStatus(messageId));

        verify(messageRepository, times(1)).findById(messageId);
        verify(messageRepository, never()).save(any());
    }

    @Test
    void testGetAllMessages_ShouldReturnAllUnansweredMessages() {
        MessageEntity messageEntity1 = new MessageEntity();
        MessageDTO messageDTO1 = new MessageDTO().setTitle(TITLE + 1).setContact(EMAIL).setContent(CONTENT);
        MessageEntity messageEntity2 = new MessageEntity();
        MessageDTO messageDTO2 = new MessageDTO().setTitle(TITLE + 2).setContact(EMAIL).setContent(CONTENT);
        List<MessageEntity> messageEntities = new ArrayList<>();
        messageEntities.add(messageEntity1);
        messageEntities.add(messageEntity2);
        when(messageRepository.findAllByStatus(MessageStatus.UNANSWERED)).thenReturn(messageEntities);
        when(messageMapper.entityToMessageDto(messageEntity1)).thenReturn(messageDTO1);
        when(messageMapper.entityToMessageDto(messageEntity2)).thenReturn(messageDTO2);

        List<MessageDTO> unansweredMessages = messageService.getAllMessages();

        assertNotNull(unansweredMessages);
        assertEquals(2, unansweredMessages.size());
        assertEquals(TITLE + 1, unansweredMessages.get(0).getTitle());
        assertEquals(TITLE + 2, unansweredMessages.get(1).getTitle());

        verify(messageRepository, times(1)).findAllByStatus(MessageStatus.UNANSWERED);
        verify(messageMapper, times(1)).entityToMessageDto(messageEntity1);
        verify(messageMapper, times(1)).entityToMessageDto(messageEntity2);
    }
}