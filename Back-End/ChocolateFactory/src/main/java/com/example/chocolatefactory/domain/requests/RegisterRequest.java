package com.example.chocolatefactory.domain.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    @NotBlank
    @Size(min = 4, max = 40)
    private String password;
    @NotBlank
    @Size(min = 4, max = 40)
    private String confirmPassword;
    @NotBlank
    @Size(min = 3, max = 40)
    private String fullName;
    @NotBlank
    @Size(min = 3, max = 40)
    private String city;
    @NotBlank
    @Size(min = 3, max = 40)
    private String address;
    @NotBlank
    @Size(min = 6, max = 20)
    private String phone;

    public String getEmail() {
        return email;
    }

    public RegisterRequest setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public RegisterRequest setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public RegisterRequest setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public RegisterRequest setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getCity() {
        return city;
    }

    public RegisterRequest setCity(String city) {
        this.city = city;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public RegisterRequest setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public RegisterRequest setPhone(String phone) {
        this.phone = phone;
        return this;
    }
}
