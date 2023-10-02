package com.example.chocolatefactory.domain.responses;

import java.util.List;

public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String email;
    private String fullName;
    private List<String> roles;

    public JwtResponse(String token, Long id, String email, String fullName, List<String> roles) {
        this.token = token;
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.roles = roles;
    }

    public String getToken() {
        return token;
    }

    public JwtResponse setToken(String token) {
        this.token = token;
        return this;
    }

    public String getType() {
        return type;
    }

    public JwtResponse setType(String type) {
        this.type = type;
        return this;
    }

    public Long getId() {
        return id;
    }

    public JwtResponse setId(Long id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public JwtResponse setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public JwtResponse setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public List<String> getRoles() {
        return roles;
    }

    public JwtResponse setRoles(List<String> roles) {
        this.roles = roles;
        return this;
    }
}
