package com.example.chocolatefactory.domain.responseDTOs.message;

public class MessageDTO {
    private Long id;
    private String title;
    private String contact;
    private String content;

    public Long getId() {
        return id;
    }

    public MessageDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public MessageDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContact() {
        return contact;
    }

    public MessageDTO setContact(String contact) {
        this.contact = contact;
        return this;
    }

    public String getContent() {
        return content;
    }

    public MessageDTO setContent(String content) {
        this.content = content;
        return this;
    }
}
