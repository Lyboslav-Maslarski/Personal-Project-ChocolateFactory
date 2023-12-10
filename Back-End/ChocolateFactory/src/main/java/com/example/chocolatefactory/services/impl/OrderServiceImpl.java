package com.example.chocolatefactory.services.impl;

import com.example.chocolatefactory.domain.AppUserDetails;
import com.example.chocolatefactory.domain.entities.OrderEntity;
import com.example.chocolatefactory.domain.entities.ProductEntity;
import com.example.chocolatefactory.domain.entities.UserEntity;
import com.example.chocolatefactory.domain.enums.OrderStatus;
import com.example.chocolatefactory.domain.requestDTOs.order.OrderAddDTO;
import com.example.chocolatefactory.domain.requestDTOs.order.OrderIdDTO;
import com.example.chocolatefactory.domain.responseDTOs.order.OrderDTO;
import com.example.chocolatefactory.domain.responseDTOs.order.OrderDetailsDTO;
import com.example.chocolatefactory.event.OnOrderPlacedEvent;
import com.example.chocolatefactory.exceptions.AppException;
import com.example.chocolatefactory.mappers.OrderMapper;
import com.example.chocolatefactory.repositories.OrderRepository;
import com.example.chocolatefactory.repositories.ProductRepository;
import com.example.chocolatefactory.repositories.UserRepository;
import com.example.chocolatefactory.services.OrderService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;
    private final ApplicationEventPublisher eventPublisher;

    public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository,
                            UserRepository userRepository, OrderMapper orderMapper, ApplicationEventPublisher eventPublisher) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.orderMapper = orderMapper;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public List<OrderDTO> getAllOrdersByUserId(Long id) {
        return orderRepository.findByBuyerId(id)
                .stream()
                .filter(o -> !o.getStatus().equals(OrderStatus.CANCELLED))
                .map(orderMapper::toOrderDTO)
                .collect(Collectors.toList());
    }


    @Override
    public OrderDTO saveOrder(OrderAddDTO orderAddDTO, AppUserDetails appUserDetails) {
        List<ProductEntity> productEntities = orderAddDTO.products().stream()
                .map(id -> productRepository.findById(id)
                        .orElseThrow(() -> new AppException("Product with id " + id + "not found!", HttpStatus.NOT_FOUND)))
                .toList();

        UserEntity buyer = userRepository.findById(appUserDetails.getId())
                .orElseThrow(() -> new AppException("User not found!", HttpStatus.NOT_FOUND));

        OrderEntity orderEntity = new OrderEntity()
                .setProducts(productEntities)
                .setOrderNumber(UUID.randomUUID())
                .setStatus(OrderStatus.WAITING)
                .setBuyer(buyer)
                .setTotal(productEntities.stream().map(ProductEntity::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add));

        OrderEntity saved = orderRepository.save(orderEntity);

        eventPublisher.publishEvent(new OnOrderPlacedEvent(this, buyer.getId(), saved.getTotal()));

        return orderMapper.toOrderDTO(saved);
    }

    @Override
    public OrderDetailsDTO getOrder(UUID orderNumber) {
        OrderEntity order = orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new AppException("Order not found!", HttpStatus.NOT_FOUND));

        return orderMapper.toOrderDetailsDTO(order);
    }

    @Override
    public void deleteOrder(Long id) {
        OrderEntity orderEntity = orderRepository.findById(id)
                .orElseThrow(() -> new AppException("Order not found!", HttpStatus.NOT_FOUND));

        orderEntity.setStatus(OrderStatus.CANCELLED);

        orderRepository.save(orderEntity);
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        List<OrderEntity> allOrders = orderRepository.findAllByStatusNotOrderByStatus(OrderStatus.CANCELLED);

        return allOrders.stream()
                .map(orderMapper::toOrderDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void acceptOrder(OrderIdDTO orderIdDTO) {
        OrderEntity orderEntity = orderRepository.findById(orderIdDTO.id())
                .orElseThrow(() -> new AppException("Order not found!", HttpStatus.NOT_FOUND));

        orderEntity.setStatus(OrderStatus.ACCEPTED);

        orderRepository.save(orderEntity);
    }

    @Override
    public void dispatchOrder(OrderIdDTO orderIdDTO) {
        OrderEntity orderEntity = orderRepository.findById(orderIdDTO.id())
                .orElseThrow(() -> new AppException("Order not found!", HttpStatus.NOT_FOUND));

        orderEntity.setStatus(OrderStatus.SHIPPED);

        orderRepository.save(orderEntity);
    }
}
