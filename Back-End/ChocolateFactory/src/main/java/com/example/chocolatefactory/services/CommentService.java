package com.example.chocolatefactory.services;

import com.example.chocolatefactory.domain.AppUserDetails;
import com.example.chocolatefactory.domain.entities.CommentEntity;
import com.example.chocolatefactory.domain.requestDTOs.comment.CommentAddDTO;
import com.example.chocolatefactory.domain.responseDTOs.comment.CommentDTO;
import com.example.chocolatefactory.mappers.CommentMapper;
import com.example.chocolatefactory.repositories.CommentRepository;
import com.example.chocolatefactory.repositories.ProductRepository;
import com.example.chocolatefactory.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CommentMapper commentMapper;

    public CommentService(CommentRepository commentRepository, UserRepository userRepository,
                          ProductRepository productRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.commentMapper = commentMapper;
    }

    public CommentDTO saveComment(CommentAddDTO commentAddDTO, AppUserDetails appUserDetails) {
        CommentEntity commentEntity = commentMapper.commentDTOToEntity(commentAddDTO);

        commentEntity.setUser(userRepository.findById(appUserDetails.getId()).get());
        commentEntity.setProduct(productRepository.findById(commentAddDTO.productId()).get());

        CommentEntity saved = commentRepository.save(commentEntity);

        return commentMapper.commentEntityToCommentDTO(saved);
    }
}
