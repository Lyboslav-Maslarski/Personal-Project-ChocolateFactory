package com.example.chocolatefactory.domain.requestDTOs.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CommentAddDTO(@NotBlank @Size(min = 6) String text, @NotNull Long productId) {
}
