package com.example.chocolatefactory.services;

import com.example.chocolatefactory.domain.AppUserDetails;
import com.example.chocolatefactory.domain.requestDTOs.order.OrderAddDTO;
import com.example.chocolatefactory.domain.requestDTOs.order.OrderIdDTO;
import com.example.chocolatefactory.domain.responseDTOs.order.OrderDTO;
import com.example.chocolatefactory.domain.responseDTOs.order.OrderDetailsDTO;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    List<OrderDTO> getAllOrdersByUserId(Long id);

    OrderDTO saveOrder(OrderAddDTO orderAddDTO, AppUserDetails appUserDetails);

    OrderDetailsDTO getOrder(UUID orderNumber);

    void deleteOrder(Long id);

    List<OrderDTO> getAllOrders();

    void acceptOrder(OrderIdDTO orderIdDTO);

    void dispatchOrder(OrderIdDTO orderIdDTO);
}
