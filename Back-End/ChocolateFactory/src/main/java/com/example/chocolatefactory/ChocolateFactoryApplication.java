package com.example.chocolatefactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ChocolateFactoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChocolateFactoryApplication.class, args);
	}

}
