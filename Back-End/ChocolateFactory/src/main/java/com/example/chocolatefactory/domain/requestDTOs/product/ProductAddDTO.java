package com.example.chocolatefactory.domain.requestDTOs.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProductAddDTO(@NotBlank @Size(min = 6, max = 20) String name,
                            @NotBlank @Size(min = 6) String description,
                            @NotBlank @Size(min = 6, max = 100) String imageUrl,
                            @NotNull @Positive Integer quantity,
                            @NotNull @Positive BigDecimal price) {
}
