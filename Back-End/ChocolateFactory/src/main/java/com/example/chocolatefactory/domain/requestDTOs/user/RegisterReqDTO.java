package com.example.chocolatefactory.domain.requestDTOs.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterReqDTO(@NotBlank @Size(min = 10, max = 30) String email,
                             @NotNull @Size(min = 6, max = 20) char[] password,
                             @NotBlank @Size(min = 5, max = 30) String fullName,
                             @NotBlank @Size(min = 3, max = 10) String city,
                             @NotBlank @Size(min = 10, max = 100) String address,
                             @NotBlank @Size(min = 6, max = 20) String phone) {
}
