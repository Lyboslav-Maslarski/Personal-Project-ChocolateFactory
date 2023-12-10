package com.example.chocolatefactory.aop;

import com.example.chocolatefactory.domain.responseDTOs.order.OrderDTO;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SampleAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(SampleAspect.class);

    @Pointcut("execution(* com.example.chocolatefactory.services.impl.OrderServiceImpl.saveOrder(..))")
    void placeOrder() {
    }

    @Around("placeOrder()")
    public Object logOrderPlaced(ProceedingJoinPoint joinPoint) throws Throwable {

        OrderDTO object = (OrderDTO) joinPoint.proceed();

        LOGGER.info("Order with number [{}] and total [{}] was placed!",
                object.getOrderNumber(), object.getTotal());

        return object;
    }
}
