package com.example.flexbuy.order.service;

import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.flexbuy.common.exception.CustomException.UnwantedException;
import com.example.flexbuy.order.model.Order;
import com.example.flexbuy.order.repository.OrderRepository;
import com.example.flexbuy.user.model.User;
import com.example.flexbuy.user.repository.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ReadOrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public List<Order> fetchMyOrder(String email){
        User user = userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException(email));
        List<Order> orders = orderRepository.findByUser(user);

        if(orders.isEmpty()){
            String message = "No orders available for this user";
            throw new UnwantedException(message);
        }

        return orders;
    }
}
