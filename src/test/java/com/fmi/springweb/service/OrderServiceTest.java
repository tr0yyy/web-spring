package com.fmi.springweb.service;

import com.fmi.springweb.exceptions.OrderFailedException;
import com.fmi.springweb.model.OrderEntity;
import com.fmi.springweb.model.UserEntity;
import com.fmi.springweb.repository.OrderRepository;
import com.fmi.springweb.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("UnitTests")
public class OrderServiceTest {
    @Mock
    private UserService userService;
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setup() {
        doNothing().when(userService).addOrderToUser(any(UserEntity.class), any(OrderEntity.class));
    }

    @Test
    public void testCompleteOrderSuccess() {
        String paymentToken = "dXNlcm5hbWUxMjN8MTAwMA=="; // base64 encoded: "username123|1000"
        UserEntity user = new UserEntity();
        user.setUsername("username123");

        when(userRepository.findByUsername("username123")).thenReturn(Optional.of(user));

        assertDoesNotThrow(() -> orderService.completeOrder(paymentToken));

        verify(orderRepository, times(1)).save(any(OrderEntity.class));

    }

    @Test
    public void testCompleteOrderInvalidPaymentData() {
        String paymentToken = "dXNlcm5hbWUxMjN8aW52YWxpZEZ1bmRz"; // username123|invalidFunds
        UserEntity user = new UserEntity();
        user.setUsername("username123");

        when(userRepository.findByUsername("username123")).thenReturn(Optional.of(user));

        assertThrows(OrderFailedException.class, () -> orderService.completeOrder(paymentToken));
    }

}
