package com.example.chocolatefactory.domain.requestDTOs.comment;

import jakarta.validation.constraints.NotNull;

public record CommentIdDTO(@NotNull Long id) {
}
