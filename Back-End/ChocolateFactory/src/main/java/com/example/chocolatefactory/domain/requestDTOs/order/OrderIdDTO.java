package com.example.chocolatefactory.domain.requestDTOs.order;

import jakarta.validation.constraints.NotNull;

public record OrderIdDTO(@NotNull Long id) {
}
