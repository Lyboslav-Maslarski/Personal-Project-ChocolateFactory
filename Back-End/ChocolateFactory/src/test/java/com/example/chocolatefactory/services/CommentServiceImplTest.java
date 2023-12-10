package com.example.chocolatefactory.services;

import com.example.chocolatefactory.domain.AppUserDetails;
import com.example.chocolatefactory.domain.entities.CommentEntity;
import com.example.chocolatefactory.domain.entities.ProductEntity;
import com.example.chocolatefactory.domain.entities.UserEntity;
import com.example.chocolatefactory.domain.requestDTOs.comment.CommentAddDTO;
import com.example.chocolatefactory.domain.responseDTOs.comment.CommentDTO;
import com.example.chocolatefactory.mappers.CommentMapper;
import com.example.chocolatefactory.repositories.CommentRepository;
import com.example.chocolatefactory.repositories.ProductRepository;
import com.example.chocolatefactory.repositories.UserRepository;
import com.example.chocolatefactory.services.impl.CommentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CommentServiceImplTest {
    public static final String TEXT_COMMENT = "text comment";
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private CommentMapper commentMapper;
    @InjectMocks
    private CommentServiceImpl commentServiceImpl;

    @BeforeEach
    void setUp() {
        commentServiceImpl = new CommentServiceImpl(commentRepository, userRepository, productRepository, commentMapper);
    }

    @Test
    void testSaveComment_ShouldSaveComment() {
        long id = 1L;
        CommentAddDTO commentAddDTO = new CommentAddDTO(TEXT_COMMENT, id);
        AppUserDetails appUserDetails = new AppUserDetails("username", "password", Collections.emptyList());
        appUserDetails.setId(id);
        UserEntity userEntity = new UserEntity();
        userEntity.setId(id);
        CommentEntity commentEntity = new CommentEntity().setText(TEXT_COMMENT);
        CommentDTO expected = new CommentDTO().setText(TEXT_COMMENT);

        when(commentMapper.commentDTOToEntity(commentAddDTO)).thenReturn(commentEntity);
        when(userRepository.findById(appUserDetails.getId())).thenReturn(Optional.of(userEntity));
        when(productRepository.findById(commentAddDTO.productId())).thenReturn(Optional.of(new ProductEntity()));
        when(commentRepository.save(commentEntity)).thenReturn(commentEntity);
        when(commentMapper.commentEntityToCommentDTO(commentEntity)).thenReturn(expected);

        CommentDTO savedComment = commentServiceImpl.saveComment(commentAddDTO, appUserDetails);

        assertNotNull(savedComment);
        assertEquals(TEXT_COMMENT,savedComment.getText());

        verify(commentMapper, times(1)).commentDTOToEntity(commentAddDTO);
        verify(userRepository, times(1)).findById(appUserDetails.getId());
        verify(productRepository, times(1)).findById(commentAddDTO.productId());
        verify(commentRepository, times(1)).save(commentEntity);
        verify(commentMapper, times(1)).commentEntityToCommentDTO(commentEntity);
    }

    @Test
    void testDeleteComment_ShouldDeleteComment() {
        Long commentId = 1L;

        assertDoesNotThrow(() -> commentServiceImpl.deleteComment(commentId));

        verify(commentRepository, times(1)).deleteById(commentId);
    }
}