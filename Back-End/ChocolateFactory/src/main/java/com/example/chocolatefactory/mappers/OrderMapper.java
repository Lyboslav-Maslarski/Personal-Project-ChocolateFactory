package com.example.chocolatefactory.mappers;

import com.example.chocolatefactory.domain.entities.OrderEntity;
import com.example.chocolatefactory.domain.responseDTOs.order.OrderDTO;
import com.example.chocolatefactory.domain.responseDTOs.order.OrderDetailsDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDTO toOrderDTO(OrderEntity orderEntity);
    OrderDetailsDTO toOrderDetailsDTO(OrderEntity orderEntity);
}
