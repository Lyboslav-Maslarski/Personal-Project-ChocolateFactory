package com.example.chocolatefactory.web;

import com.example.chocolatefactory.domain.AppUserDetails;
import com.example.chocolatefactory.domain.requestDTOs.order.OrderAddDTO;
import com.example.chocolatefactory.domain.responseDTOs.order.OrderDTO;
import com.example.chocolatefactory.domain.responseDTOs.order.OrderDetailsDTO;
import com.example.chocolatefactory.exceptions.AppException;
import com.example.chocolatefactory.services.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addOrder(@Valid @RequestBody OrderAddDTO orderAddDTO, BindingResult bindingResult,
                                      @AuthenticationPrincipal AppUserDetails appUserDetails) {
        if (bindingResult.hasErrors()) {
            throw new AppException("Invalid order data!", HttpStatus.BAD_REQUEST);
        }

        OrderDTO orderDTO = orderService.saveOrder(orderAddDTO,appUserDetails);

        return ResponseEntity.created(URI.create("api/orders/" + orderDTO.getId())).body(orderDTO);
    }

    @GetMapping("/{orderNumber}")
    public ResponseEntity<?> getOrderDetails(@PathVariable UUID orderNumber){
        OrderDetailsDTO order = orderService.getOrder(orderNumber);

        return ResponseEntity.ok(order);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id){
        orderService.deleteOrder(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrderDTO>> getAllOrders(){
        List<OrderDTO> orders = orderService.getAllOrders();

        return ResponseEntity.ok(orders);
    }
}
