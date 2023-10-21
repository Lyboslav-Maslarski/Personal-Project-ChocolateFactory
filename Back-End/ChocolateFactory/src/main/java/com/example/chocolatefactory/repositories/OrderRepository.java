package com.example.chocolatefactory.repositories;

import com.example.chocolatefactory.domain.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByBuyerId(Long id);

    Optional<OrderEntity> findByOrderNumber(UUID orderNumber);
}
