package com.fmi.springweb.service;

import com.fmi.springweb.exceptions.AuthenticationFailedException;
import com.fmi.springweb.exceptions.OrderFailedException;
import com.fmi.springweb.model.OrderEntity;
import com.fmi.springweb.model.UserEntity;
import com.fmi.springweb.repository.OrderRepository;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final UserService userService;
    private final OrderRepository orderRepository;

    public OrderService(UserService userService, OrderRepository orderRepository) {
        this.userService = userService;
        this.orderRepository = orderRepository;
    }

    public void completeOrder(String paymentToken) throws OrderFailedException {
        String[] paymentDecoded = new String(Base64.decodeBase64(paymentToken)).split("\\|");
        String username = paymentDecoded[0];
        Long funds = Long.valueOf(paymentDecoded[1]);

        try {
            UserEntity user = userService.getUser(username);
            OrderEntity order = new OrderEntity();
            order.setFundsAdded(funds);
            orderRepository.save(order);
            userService.addOrderToUser(user, order);
        } catch (AuthenticationFailedException exception) {
            throw new OrderFailedException("Could not save order due to invalid username provided");
        }
    }
}
