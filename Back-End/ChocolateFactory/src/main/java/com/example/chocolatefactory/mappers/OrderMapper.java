package com.example.chocolatefactory.mappers;

import com.example.chocolatefactory.domain.entities.OrderEntity;
import com.example.chocolatefactory.domain.responseDTOs.order.OrderDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDTO toOrderDTO(OrderEntity orderEntity);
}
