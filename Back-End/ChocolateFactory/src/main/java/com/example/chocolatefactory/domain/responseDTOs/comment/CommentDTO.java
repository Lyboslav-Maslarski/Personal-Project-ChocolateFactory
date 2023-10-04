package com.example.chocolatefactory.domain.responseDTOs.comment;

public class CommentDTO {
    private String text;
    private String user;
    private Boolean isOwner;

    public String getText() {
        return text;
    }

    public CommentDTO setText(String text) {
        this.text = text;
        return this;
    }

    public String getUser() {
        return user;
    }

    public CommentDTO setUser(String user) {
        this.user = user;
        return this;
    }

    public Boolean getOwner() {
        return isOwner;
    }

    public CommentDTO setOwner(Boolean owner) {
        isOwner = owner;
        return this;
    }
}
