package com.example.chocolatefactory.domain.responseDTOs.user;

public class UserShorDTO {
    private Long id;
    private String email;
    private Boolean isModerator;

    public Long getId() {
        return id;
    }

    public UserShorDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserShorDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public Boolean getModerator() {
        return isModerator;
    }

    public UserShorDTO setModerator(Boolean moderator) {
        isModerator = moderator;
        return this;
    }
}
