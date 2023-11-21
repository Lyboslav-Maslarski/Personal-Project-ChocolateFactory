package com.example.chocolatefactory.domain.requestDTOs.order;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderIdDTO(@NotNull @Positive Long id) {
}
