package com.example.chocolatefactory.services;

import com.example.chocolatefactory.domain.entities.MessageEntity;
import com.example.chocolatefactory.domain.enums.MessageStatus;
import com.example.chocolatefactory.repositories.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Scheduler {
    private static final Logger LOGGER = LoggerFactory.getLogger(Scheduler.class);
    private final MessageRepository messageRepository;

    @Autowired
    public Scheduler(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    //    @Scheduled(cron = "0 * * * * *")
    @Scheduled(cron = "0 0 2 * * SUN")
    public void deleteAnsweredMassages() {
        List<MessageEntity> allByStatus = messageRepository.findAllByStatus(MessageStatus.ANSWERED);

        LOGGER.info("{} messages were deleted.", allByStatus.size());

        messageRepository.deleteAll(allByStatus);
    }
}
