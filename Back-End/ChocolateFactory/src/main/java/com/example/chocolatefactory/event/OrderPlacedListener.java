package com.example.chocolatefactory.event;

import com.example.chocolatefactory.services.UserService;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class OrderPlacedListener implements ApplicationListener<OnOrderPlacedEvent> {

    private final UserService userService;

    public OrderPlacedListener(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onApplicationEvent(OnOrderPlacedEvent event) {
        userService.addBonusPoints(event.getBuyerId(), event.getTotal());
    }
}
