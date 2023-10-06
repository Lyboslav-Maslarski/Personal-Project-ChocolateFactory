package com.example.chocolatefactory.services;

import com.example.chocolatefactory.domain.entities.OrderEntity;
import com.example.chocolatefactory.domain.responseDTOs.order.OrderDTO;
import com.example.chocolatefactory.mappers.OrderMapper;
import com.example.chocolatefactory.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    public List<OrderDTO> getAllOrdersByUserId(Long id) {
        return orderRepository.findByBuyerId(id)
                .stream()
                .map(orderMapper::toOrderDTO)
                .collect(Collectors.toList());
    }


}
