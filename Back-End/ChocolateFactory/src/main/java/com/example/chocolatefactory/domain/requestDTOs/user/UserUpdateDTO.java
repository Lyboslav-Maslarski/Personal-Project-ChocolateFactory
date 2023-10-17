package com.example.chocolatefactory.domain.requestDTOs.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserUpdateDTO(@NotBlank @Size(min = 6, max = 20) String email,
                            @NotBlank @Size(min = 6, max = 30) String fullName,
                            @NotBlank @Size(min = 3, max = 10) String city,
                            @NotBlank @Size(min = 6, max = 50) String address,
                            @NotBlank @Size(min = 6, max = 20) String phone) {
}
