package com.example.flexbuy.order.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.flexbuy.common.exception.CustomException.UnwantedException;
import com.example.flexbuy.order.dto.OrderEnum;
import com.example.flexbuy.order.dto.PaymentDto;
import com.example.flexbuy.order.model.Order;
import com.example.flexbuy.order.repository.OrderRepository;
import com.example.flexbuy.user.model.User;
import com.example.flexbuy.user.repository.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class PaymentService {
    // this service will update status for the order to PAID and and store the record as order that is paid so user from frontend can categorize unpaid and paid
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public Order completePayment(PaymentDto data,String email){
        // get order for this user
        User user = userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException(email));
        String message = "No  Orders Found For: "+email;
        Order order = orderRepository.findByIdAndUserAndStatus(data.getOrderId(), user, OrderEnum.PENDING).orElseThrow(()-> new UnwantedException(message));

        // check if price matches
        if(!order.getTotalPrice().equals(data.getAmount())){
            String amountMessage = "only exact amount is required!";
            throw new UnwantedException(amountMessage);

        }

        order.setStatus(OrderEnum.PAID);

        return orderRepository.save(order);
    }
}
