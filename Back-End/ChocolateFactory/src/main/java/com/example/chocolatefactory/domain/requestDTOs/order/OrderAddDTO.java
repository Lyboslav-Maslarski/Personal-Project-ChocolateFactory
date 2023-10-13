package com.example.chocolatefactory.domain.requestDTOs.order;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record OrderAddDTO(@NotNull @Size(min = 1) List<Long> products){
}
