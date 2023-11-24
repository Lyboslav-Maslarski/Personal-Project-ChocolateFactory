package com.example.chocolatefactory.domain.entities;

import com.example.chocolatefactory.domain.enums.MessageStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "messages")
public class MessageEntity extends BaseEntity {
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String contact;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MessageStatus status;

    public String getTitle() {
        return title;
    }

    public MessageEntity setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContact() {
        return contact;
    }

    public MessageEntity setContact(String contact) {
        this.contact = contact;
        return this;
    }

    public String getContent() {
        return content;
    }

    public MessageEntity setContent(String content) {
        this.content = content;
        return this;
    }

    public MessageStatus getStatus() {
        return status;
    }

    public MessageEntity setStatus(MessageStatus status) {
        this.status = status;
        return this;
    }
}
