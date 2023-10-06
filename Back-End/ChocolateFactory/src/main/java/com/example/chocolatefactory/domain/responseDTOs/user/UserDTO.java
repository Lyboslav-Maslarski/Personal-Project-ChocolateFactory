package com.example.chocolatefactory.domain.responseDTOs.user;

import com.example.chocolatefactory.domain.enums.RoleEnum;

import java.util.Set;

public class UserDTO {
    private Long id;
    private String token;
    private String email;
    private String fullName;
    private String city;
    private String address;
    private String phone;
    private Set<String> roles;

    public Long getId() {
        return id;
    }

    public UserDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getToken() {
        return token;
    }

    public UserDTO setToken(String token) {
        this.token = token;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public UserDTO setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getCity() {
        return city;
    }

    public UserDTO setCity(String city) {
        this.city = city;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public UserDTO setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public UserDTO setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public UserDTO setRoles(Set<String> roles) {
        this.roles = roles;
        return this;
    }
}
