package com.example.chocolatefactory.services;

import com.example.chocolatefactory.domain.AppUserDetails;
import com.example.chocolatefactory.domain.entities.OrderEntity;
import com.example.chocolatefactory.domain.entities.ProductEntity;
import com.example.chocolatefactory.domain.entities.UserEntity;
import com.example.chocolatefactory.domain.enums.OrderStatus;
import com.example.chocolatefactory.domain.requestDTOs.order.OrderAddDTO;
import com.example.chocolatefactory.domain.requestDTOs.order.OrderIdDTO;
import com.example.chocolatefactory.domain.responseDTOs.order.OrderDTO;
import com.example.chocolatefactory.domain.responseDTOs.order.OrderDetailsDTO;
import com.example.chocolatefactory.exceptions.AppException;
import com.example.chocolatefactory.mappers.OrderMapper;
import com.example.chocolatefactory.repositories.OrderRepository;
import com.example.chocolatefactory.repositories.ProductRepository;
import com.example.chocolatefactory.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.context.ApplicationEventPublisher;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class OrderServiceTest {
    public static final OrderStatus ORDER_STATUS_1 = OrderStatus.WAITING;
    public static final BigDecimal TOTAL_1 = BigDecimal.TEN;
    public static final OrderStatus ORDER_STATUS_2 = OrderStatus.ACCEPTED;
    public static final BigDecimal TOTAL_2 = BigDecimal.valueOf(20);
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private OrderMapper orderMapper;
    @Mock
    private ApplicationEventPublisher eventPublisher;
    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderService = new OrderService(orderRepository, productRepository, userRepository, orderMapper, eventPublisher);
    }

    @Test
    void testGetAllOrdersByUserId_ShouldReturnListOfOrders() {
        Long userId = 1L;
        List<OrderEntity> orders = new ArrayList<>();

        OrderEntity order1 = new OrderEntity().setStatus(ORDER_STATUS_1).setTotal(TOTAL_1);
        OrderDTO orderDTO1 = new OrderDTO().setStatus(ORDER_STATUS_1.name()).setTotal(TOTAL_1);
        OrderEntity order2 = new OrderEntity().setStatus(ORDER_STATUS_2).setTotal(TOTAL_2);
        OrderDTO orderDTO2 = new OrderDTO().setStatus(ORDER_STATUS_2.name()).setTotal(TOTAL_2);
        OrderEntity order3 = new OrderEntity().setStatus(OrderStatus.CANCELLED);

        orders.add(order1);
        orders.add(order2);
        orders.add(order3);

        when(orderRepository.findByBuyerId(userId)).thenReturn(orders);
        when(orderMapper.toOrderDTO(order1)).thenReturn(orderDTO1);
        when(orderMapper.toOrderDTO(order2)).thenReturn(orderDTO2);

        List<OrderDTO> result = orderService.getAllOrdersByUserId(userId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(orderDTO1.getStatus(), result.get(0).getStatus());
        assertEquals(orderDTO1.getTotal(), result.get(0).getTotal());
        assertEquals(orderDTO2.getStatus(), result.get(1).getStatus());
        assertEquals(orderDTO2.getTotal(), result.get(1).getTotal());

        verify(orderRepository, times(1)).findByBuyerId(userId);
        verify(orderMapper, times(1)).toOrderDTO(order1);
        verify(orderMapper, times(1)).toOrderDTO(order2);
    }

    @Test
    void testSaveOrder_ShouldCreateOrderSuccessfully() {
        Long userId = 1L;
        UserEntity userEntity = new UserEntity();
        AppUserDetails appUserDetails = new AppUserDetails("username", "password", Collections.emptyList());
        appUserDetails.setId(userId);
        userEntity.setId(userId);

        UUID orderNumber = UUID.randomUUID();
        List<Long> productIds = List.of(1L, 2L, 3L);
        BigDecimal total = BigDecimal.valueOf(50);

        ProductEntity product1 = new ProductEntity().setPrice(BigDecimal.valueOf(15));
        ProductEntity product2 = new ProductEntity().setPrice(BigDecimal.valueOf(20));
        ProductEntity product3 = new ProductEntity().setPrice(BigDecimal.valueOf(15));
        when(productRepository.findById(anyLong())).thenAnswer(invocation -> {
            Long id = invocation.getArgument(0);
            if (id.equals(1L)) return Optional.of(product1);
            else if (id.equals(2L)) return Optional.of(product2);
            else if (id.equals(3L)) return Optional.of(product3);
            else return Optional.empty();
        });
        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));

        OrderEntity savedOrderEntity = new OrderEntity()
                .setOrderNumber(orderNumber)
                .setStatus(OrderStatus.WAITING)
                .setBuyer(userEntity)
                .setTotal(total);
        when(orderRepository.save(any())).thenReturn(savedOrderEntity);

        OrderDTO orderDTO = new OrderDTO()
                .setOrderNumber(orderNumber)
                .setStatus(OrderStatus.WAITING.name())
                .setTotal(total);

        when(orderMapper.toOrderDTO(savedOrderEntity)).thenReturn(orderDTO);

        OrderAddDTO orderAddDTO = new OrderAddDTO(productIds);

        OrderDTO result = orderService.saveOrder(orderAddDTO, appUserDetails);

        assertNotNull(result);
        assertEquals(OrderStatus.WAITING.name(), result.getStatus());
        assertEquals(total, result.getTotal());

        verify(productRepository, times(productIds.size())).findById(anyLong());
        verify(userRepository, times(1)).findById(userId);
        verify(orderRepository, times(1)).save(any());
        verify(orderMapper, times(1)).toOrderDTO(savedOrderEntity);
    }

    @Test
    void testGetOrder_ShouldReturnOrderDetails() {
        UUID orderNumber = UUID.randomUUID();
        OrderEntity orderEntity = new OrderEntity()
                .setOrderNumber(orderNumber)
                .setStatus(OrderStatus.ACCEPTED)
                .setTotal(BigDecimal.valueOf(50));
        OrderDetailsDTO expected = new OrderDetailsDTO()
                .setOrderNumber(orderNumber)
                .setStatus(OrderStatus.ACCEPTED.name())
                .setTotal(BigDecimal.valueOf(50));

        when(orderRepository.findByOrderNumber(orderNumber)).thenReturn(Optional.of(orderEntity));
        when(orderMapper.toOrderDetailsDTO(orderEntity)).thenReturn(expected);

        OrderDetailsDTO result = orderService.getOrder(orderNumber);

        assertNotNull(result);
        assertEquals(orderNumber, result.getOrderNumber());
        assertEquals(BigDecimal.valueOf(50), result.getTotal());

        verify(orderRepository, times(1)).findByOrderNumber(orderNumber);
        verify(orderMapper, times(1)).toOrderDetailsDTO(orderEntity);
    }

    @Test
    void testDeleteOrder_ShouldCancelOrderSuccessfully() {
        Long orderId = 1L;
        OrderEntity orderEntity = new OrderEntity().setStatus(OrderStatus.WAITING);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(orderEntity));
        when(orderRepository.save(orderEntity)).thenReturn(orderEntity);

        assertDoesNotThrow(() -> orderService.deleteOrder(orderId));

        assertEquals(OrderStatus.CANCELLED, orderEntity.getStatus());

        verify(orderRepository, times(1)).findById(orderId);
        verify(orderRepository, times(1)).save(orderEntity);
    }

    @Test
    void testDeleteOrder_WhenOrderNotFound_ShouldThrowException() {
        Long orderId = 1L;

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(AppException.class, () -> orderService.deleteOrder(orderId));

        verify(orderRepository, times(1)).findById(orderId);
        verify(orderRepository, never()).save(any());
    }

    @Test
    void testGetAllOrders_ShouldReturnListOfOrdersExcludingCancelled() {
        List<OrderEntity> orders = new ArrayList<>();
        OrderEntity order1 = new OrderEntity().setStatus(OrderStatus.WAITING).setTotal(BigDecimal.valueOf(10));
        OrderDTO orderDTO1 = new OrderDTO().setStatus(OrderStatus.WAITING.name()).setTotal(BigDecimal.valueOf(10));
        OrderEntity order2 = new OrderEntity().setStatus(OrderStatus.ACCEPTED).setTotal(BigDecimal.valueOf(20));
        OrderDTO orderDTO2 = new OrderDTO().setStatus(OrderStatus.ACCEPTED.name()).setTotal(BigDecimal.valueOf(20));
        OrderEntity order3 = new OrderEntity().setStatus(OrderStatus.CANCELLED).setTotal(BigDecimal.valueOf(30));
        orders.add(order1);
        orders.add(order2);

        when(orderRepository.findAllByStatusNotOrderByStatus(OrderStatus.CANCELLED)).thenReturn(orders);
        when(orderMapper.toOrderDTO(order1)).thenReturn(orderDTO1);
        when(orderMapper.toOrderDTO(order2)).thenReturn(orderDTO2);

        List<OrderDTO> result = orderService.getAllOrders();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(OrderStatus.WAITING.name(), result.get(0).getStatus());
        assertEquals(OrderStatus.ACCEPTED.name(), result.get(1).getStatus());

        verify(orderRepository, times(1)).findAllByStatusNotOrderByStatus(OrderStatus.CANCELLED);
        verify(orderMapper, times(1)).toOrderDTO(order1);
        verify(orderMapper, times(1)).toOrderDTO(order2);
        verify(orderMapper, never()).toOrderDTO(order3);
    }

    @Test
    void testAcceptOrder_ShouldUpdateOrderStatusToAccepted() {
        Long orderId = 1L;
        OrderEntity orderEntity = new OrderEntity().setStatus(OrderStatus.WAITING);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(orderEntity));
        when(orderRepository.save(orderEntity)).thenReturn(orderEntity);

        assertDoesNotThrow(() -> orderService.acceptOrder(new OrderIdDTO(orderId)));

        assertEquals(OrderStatus.ACCEPTED, orderEntity.getStatus());

        verify(orderRepository, times(1)).findById(orderId);
        verify(orderRepository, times(1)).save(orderEntity);
    }

    @Test
    void testAcceptOrder_WhenOrderNotFound_ShouldThrowException() {
        Long orderId = 1L;

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(AppException.class, () -> orderService.acceptOrder(new OrderIdDTO(orderId)));

        verify(orderRepository, times(1)).findById(orderId);
        verify(orderRepository, never()).save(any());
    }

    @Test
    void testDispatchOrder_ShouldUpdateOrderStatusToShipped() {
        Long orderId = 1L;
        OrderEntity orderEntity = new OrderEntity().setStatus(OrderStatus.ACCEPTED);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(orderEntity));
        when(orderRepository.save(orderEntity)).thenReturn(orderEntity);

        assertDoesNotThrow(() -> orderService.dispatchOrder(new OrderIdDTO(orderId)));

        assertEquals(OrderStatus.SHIPPED, orderEntity.getStatus());

        verify(orderRepository, times(1)).findById(orderId);
        verify(orderRepository, times(1)).save(orderEntity);
    }

    @Test
    void testDispatchOrder_WhenOrderNotFound_ShouldThrowException() {
        Long orderId = 1L;

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(AppException.class, () -> orderService.dispatchOrder(new OrderIdDTO(orderId)));

        verify(orderRepository, times(1)).findById(orderId);
        verify(orderRepository, never()).save(any());
    }
}