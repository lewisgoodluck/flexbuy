package com.example.flexbuy.order.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.flexbuy.order.model.cartModels.Cart;
import com.example.flexbuy.order.model.cartModels.CartItems;
import com.example.flexbuy.order.repository.CartItemsRepository;
import com.example.flexbuy.order.repository.CartRepository;
import com.example.flexbuy.product.model.Product;
import com.example.flexbuy.product.repository.ProductRepository;
import com.example.flexbuy.user.model.User;
import com.example.flexbuy.user.repository.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CartService {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartItemsRepository cartItemsRepository;
    private final UserRepository userRepository;

    // check if user has cart and create one if they never had one
    public Cart getOrCreateCart(User user){
        return cartRepository.findByUser(user).orElseGet(()->{
            Cart cart = new Cart();
            cart.setUser(user);
            cart.setTotalValue(BigDecimal.ZERO);
            return cartRepository.save(cart);
        });
    }

    // login to add items to cart
    public Cart addToCart(String email,Long productId,int quantity){
        // check user if has cart
        User user = userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException(email));

        Product product = productRepository.findById(productId).orElseThrow(()-> new RuntimeException("Product not found"));

        // check if user has cart or create one
        Cart cart = getOrCreateCart(user);

        // check if cart already has item
        Optional<CartItems> existingCartItem = cart.getCartItems().stream().filter(item-> item.getProductId().equals(productId)).findFirst();

        // add quantity and price if item already exist in cart else add all values as a new item in cart
        if(existingCartItem.isPresent()){
            CartItems items = existingCartItem.get();
            items.setQuantity(items.getQuantity() + quantity);
            items.setTotal(product.getPrice().multiply(BigDecimal.valueOf(items.getQuantity())));
        }else{
            CartItems newItems = new CartItems();
            newItems.setCart(cart);
            newItems.setProductId(productId);
            newItems.setQuantity(quantity);
            newItems.setPrice(product.getPrice());
            newItems.setTotal(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
            cart.getCartItems().add(newItems);
        }
        BigDecimal total = cart.getCartItems().stream().map(CartItems::getTotal).reduce(BigDecimal.ZERO,BigDecimal::add);

        cart.setTotalValue(total);

        return cartRepository.save(cart);
    }

    public Cart viewCart(String email){
        User user = userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException(email));

        return getOrCreateCart(user);
    }

    public void deleteCart(Cart cart){
        cartItemsRepository.deleteAll(cart.getCartItems());
        cart.getCartItems().clear();
        cart.setTotalValue(BigDecimal.ZERO);
        cartRepository.save(cart);
    }
}
