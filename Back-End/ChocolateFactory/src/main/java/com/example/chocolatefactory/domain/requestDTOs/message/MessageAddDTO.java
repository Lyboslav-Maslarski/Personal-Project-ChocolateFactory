package com.example.chocolatefactory.domain.requestDTOs.message;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MessageAddDTO(@NotBlank @Size(min = 6) String title,
                            @NotBlank @Size(min = 10,max = 30) @Email String contact,
                            @NotBlank @Size(min = 6) String content) {
}
