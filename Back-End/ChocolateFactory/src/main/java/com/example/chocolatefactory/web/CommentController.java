package com.example.chocolatefactory.web;

import com.example.chocolatefactory.services.CommentService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
}
