package com.example.chocolatefactory.event;

import com.example.chocolatefactory.services.impl.UserServiceImpl;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class OrderPlacedListener implements ApplicationListener<OnOrderPlacedEvent> {

    private final UserServiceImpl userServiceImpl;

    public OrderPlacedListener(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @Override
    public void onApplicationEvent(OnOrderPlacedEvent event) {
        userServiceImpl.addBonusPoints(event.getBuyerId(), event.getTotal());
    }
}
