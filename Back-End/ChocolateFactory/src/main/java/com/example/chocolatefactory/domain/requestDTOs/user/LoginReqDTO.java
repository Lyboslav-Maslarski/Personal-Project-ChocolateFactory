package com.example.chocolatefactory.domain.requestDTOs.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginReqDTO(@NotBlank @Size(min = 6, max = 20) String email,
                          @NotBlank @Size(min = 6, max = 20) char[] password) {
}
