package com.example.chocolatefactory.repositories;

import com.example.chocolatefactory.domain.entities.MessageEntity;
import com.example.chocolatefactory.domain.enums.MessageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Long> {
    List<MessageEntity> findAllByStatus(MessageStatus messageStatus);
}
