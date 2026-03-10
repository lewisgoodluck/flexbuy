package com.example.flexbuy.order.service;

import java.math.BigDecimal;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.flexbuy.order.dto.OrderEnum;
import com.example.flexbuy.order.model.Order;
import com.example.flexbuy.order.model.OrderItems;
import com.example.flexbuy.order.model.cartModels.Cart;
import com.example.flexbuy.order.model.cartModels.CartItems;
import com.example.flexbuy.order.repository.CartRepository;
import com.example.flexbuy.order.repository.OrderItemsRepository;
import com.example.flexbuy.order.repository.OrderRepository;
import com.example.flexbuy.product.dto.ProductStockUpdate;
import com.example.flexbuy.product.service.UpdateProductService;
import com.example.flexbuy.user.model.User;
import com.example.flexbuy.user.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class PlaceOrderService {

    private final OrderRepository orderRepository;
    private final OrderItemsRepository orderItemsRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final CartService cartService;
    private final UpdateProductService updateProductService;

    @Transactional
    public Order checkout(String email){
        /* step 1
        get user and cart that belong to this user
        */ 

        User user = userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException(email));
        Cart cart = cartRepository.findByUser(user).orElseThrow(()-> new RuntimeException("user has no cart available"));

        if(cart.getCartItems().isEmpty()){
            throw new RuntimeException("can not checkout empty cart");
        }

        // create order from cart 
        Order myOrder = new Order();
        myOrder.setUser(user);
        myOrder.setStatus(OrderEnum.PENDING);
        // calculate total
        BigDecimal total = cart.getCartItems().stream().map(CartItems::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
        myOrder.setTotalPrice(total);
        myOrder = orderRepository.save(myOrder); // this instantiates the save but doesnt save yet

        // loop through cart items and assign them to order table (ctrl + c / ctrl+v)
        for(CartItems cartItems: cart.getCartItems()){
            OrderItems items = new OrderItems();
            items.setOrder(myOrder);
            items.setProductId(cartItems.getProductId());
            items.setQuantity(cartItems.getQuantity());
            items.setPrice(cartItems.getPrice());
            items.setTotal(cartItems.getTotal());

            orderItemsRepository.save(items);

            ProductStockUpdate stock = new ProductStockUpdate();
            stock.setProductId(items.getProductId());
            stock.setQuantity(items.getQuantity());

            updateProductService.updateStock(stock);
        }

        cartService.deleteCart(cart); // delete cart because it's already copied to order
        return myOrder;
    }
}
