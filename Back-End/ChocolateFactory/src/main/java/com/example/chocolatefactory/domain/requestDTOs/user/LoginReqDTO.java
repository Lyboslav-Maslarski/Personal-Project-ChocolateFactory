package com.example.chocolatefactory.domain.requestDTOs.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LoginReqDTO(@NotBlank @Size(min = 10, max = 30) String email,
                          @NotNull @Size(min = 6, max = 20) char[] password) {
}
