package com.example.chocolatefactory.services;

import com.example.chocolatefactory.domain.AppUserDetails;
import com.example.chocolatefactory.domain.entities.OrderEntity;
import com.example.chocolatefactory.domain.entities.ProductEntity;
import com.example.chocolatefactory.domain.enums.OrderStatus;
import com.example.chocolatefactory.domain.requestDTOs.order.OrderAddDTO;
import com.example.chocolatefactory.domain.responseDTOs.order.OrderDTO;
import com.example.chocolatefactory.exceptions.AppException;
import com.example.chocolatefactory.mappers.OrderMapper;
import com.example.chocolatefactory.repositories.OrderRepository;
import com.example.chocolatefactory.repositories.ProductRepository;
import com.example.chocolatefactory.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository,
                        UserRepository userRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.orderMapper = orderMapper;
    }

    public List<OrderDTO> getAllOrdersByUserId(Long id) {
        return orderRepository.findByBuyerId(id)
                .stream()
                .map(orderMapper::toOrderDTO)
                .collect(Collectors.toList());
    }


    public OrderDTO saveOrder(OrderAddDTO orderAddDTO, AppUserDetails appUserDetails) {
        List<ProductEntity> productEntities = orderAddDTO.products().stream()
                .map(id -> productRepository.findById(id)
                        .orElseThrow(() -> new AppException("Product with id " + id + "not found!", HttpStatus.NOT_FOUND)))
                .toList();

        OrderEntity orderEntity = new OrderEntity()
                .setProducts(productEntities)
                .setOrderNumber(UUID.randomUUID())
                .setStatus(OrderStatus.WAITING)
                .setBuyer(userRepository.findById(appUserDetails.getId())
                        .orElseThrow(() -> new AppException("User not found!", HttpStatus.NOT_FOUND)))
                .setTotal(productEntities.stream().map(ProductEntity::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add));

        OrderEntity saved = orderRepository.save(orderEntity);

        return orderMapper.toOrderDTO(saved);
    }
}
