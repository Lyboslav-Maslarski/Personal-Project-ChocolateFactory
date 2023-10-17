package com.example.chocolatefactory.domain.requestDTOs.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PasswordDTO(@NotBlank @Size(min = 6, max = 20) String oldPassword,
                          @NotBlank @Size(min = 6, max = 20) String newPassword) {
}
