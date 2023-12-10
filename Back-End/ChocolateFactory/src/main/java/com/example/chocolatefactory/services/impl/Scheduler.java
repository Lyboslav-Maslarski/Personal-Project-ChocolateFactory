package com.example.chocolatefactory.services.impl;

import com.example.chocolatefactory.domain.entities.MessageEntity;
import com.example.chocolatefactory.domain.entities.OrderEntity;
import com.example.chocolatefactory.domain.entities.UserEntity;
import com.example.chocolatefactory.domain.enums.MessageStatus;
import com.example.chocolatefactory.domain.enums.OrderStatus;
import com.example.chocolatefactory.domain.enums.UserStatus;
import com.example.chocolatefactory.repositories.MessageRepository;
import com.example.chocolatefactory.repositories.OrderRepository;
import com.example.chocolatefactory.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Scheduler {
    private static final Logger LOGGER = LoggerFactory.getLogger(Scheduler.class);
    private final MessageRepository messageRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Autowired
    public Scheduler(MessageRepository messageRepository, OrderRepository orderRepository,
                     UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    //    @Scheduled(cron = "0 * * * * *")
    @Scheduled(cron = "0 0 2 * * SUN")
    public void deleteAnsweredMassages() {
        List<MessageEntity> allByStatus = messageRepository.findAllByStatus(MessageStatus.ANSWERED);

        LOGGER.info("{} messages were deleted.", allByStatus.size());

        messageRepository.deleteAll(allByStatus);
    }

    @Scheduled(cron = "0 0 2 * * SUN")
    public void deleteShippedOrders() {
        List<OrderEntity> allByStatus = orderRepository.findAllByStatus(OrderStatus.SHIPPED);

        LOGGER.info("{} orders were deleted.", allByStatus.size());

        orderRepository.deleteAll(allByStatus);
    }

    @Scheduled(cron = "0 0 2 ? * SUN#1")
    public void deleteDeletedUsers() {
        List<UserEntity> allByStatus = userRepository.findAllByUserStatus(UserStatus.DELETED);
        List<UserEntity> forDeletion = new ArrayList<>();

        allByStatus.forEach(userEntity -> {
            List<OrderEntity> orders = orderRepository.findByBuyerId(userEntity.getId());
            if (orders.isEmpty()){
                forDeletion.add(userEntity);
            }
        });

        LOGGER.info("{} users were deleted.", forDeletion.size());

        userRepository.deleteAll(forDeletion);
    }
}
