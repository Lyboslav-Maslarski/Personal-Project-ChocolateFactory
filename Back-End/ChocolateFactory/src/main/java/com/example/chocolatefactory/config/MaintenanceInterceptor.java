package com.example.chocolatefactory.config;

import com.example.chocolatefactory.exceptions.AppException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Configuration
public class MaintenanceInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                            @NonNull HttpServletResponse response,
                            @NonNull Object handler) throws Exception {
            LocalDateTime now = LocalDateTime.now();
            if (now.getDayOfWeek()== DayOfWeek.SUNDAY  && now.getHour() == 2) {
                throw new AppException("Maintenance", HttpStatus.SERVICE_UNAVAILABLE);
            }


        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
