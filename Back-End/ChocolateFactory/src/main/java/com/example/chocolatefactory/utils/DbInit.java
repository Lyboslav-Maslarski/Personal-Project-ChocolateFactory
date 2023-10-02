package com.example.chocolatefactory.utils;

import com.example.chocolatefactory.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DbInit implements CommandLineRunner {
    private final UserService userService;

    @Autowired
    public DbInit(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) {
        userService.initRoles();
        userService.initInitialUsers();
    }

}
