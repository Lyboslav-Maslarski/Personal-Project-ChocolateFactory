package com.example.chocolatefactory.web;

import com.example.chocolatefactory.domain.AppUserDetails;
import com.example.chocolatefactory.domain.requestDTOs.comment.CommentAddDTO;
import com.example.chocolatefactory.domain.responseDTOs.comment.CommentDTO;
import com.example.chocolatefactory.domain.responseDTOs.error.ErrorDTO;
import com.example.chocolatefactory.services.impl.CommentServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentServiceImpl commentServiceImpl;

    public CommentController(CommentServiceImpl commentServiceImpl) {
        this.commentServiceImpl = commentServiceImpl;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addComment(@Valid @RequestBody CommentAddDTO commentAddDTO, BindingResult bindingResult,
                                        @AuthenticationPrincipal AppUserDetails appUserDetails) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new ErrorDTO("Invalid comment request data!"));
        }

        CommentDTO commentDTO = commentServiceImpl.saveComment(commentAddDTO, appUserDetails);

        return ResponseEntity.created(URI.create("api/comments/" + commentDTO.getId())).body(commentDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id) {
        commentServiceImpl.deleteComment(id);

        return ResponseEntity.noContent().build();
    }
}
