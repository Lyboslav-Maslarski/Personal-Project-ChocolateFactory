package com.example.chocolatefactory.domain.requestDTOs;

public record RegisterDTO(String email, char[] password, String fullName, String city, String address, String phone) {
}
