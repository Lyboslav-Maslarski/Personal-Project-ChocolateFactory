package com.example.chocolatefactory.services;

import com.example.chocolatefactory.domain.AppUserDetails;
import com.example.chocolatefactory.domain.requestDTOs.comment.CommentAddDTO;
import com.example.chocolatefactory.domain.responseDTOs.comment.CommentDTO;

public interface CommentService {
    CommentDTO saveComment(CommentAddDTO commentAddDTO, AppUserDetails appUserDetails);

    void deleteComment(Long id);
}
