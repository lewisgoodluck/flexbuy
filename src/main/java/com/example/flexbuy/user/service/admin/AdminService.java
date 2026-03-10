package com.example.flexbuy.user.service.admin;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.flexbuy.order.dto.OrderEnum;
import com.example.flexbuy.order.repository.OrderRepository;
import com.example.flexbuy.product.repository.ProductRepository;
import com.example.flexbuy.user.model.User;
import com.example.flexbuy.user.model.Vendor;
import com.example.flexbuy.user.repository.UserRepository;
import com.example.flexbuy.user.repository.VendorRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AdminService {
    // this allows admin to do everything in the system
    private final UserRepository userRepository;
    private final VendorRepository vendorRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    /* Read & delete users */
    public List<User> readAllUsers(){
        // read all users
        return userRepository.findAll();
    }

    /* Read & flag vendors */
    public List<Vendor> readAllVendors(){
        return vendorRepository.findAll();
    }

    /* delete users */
    public void deleteUser(String email){
        User user = userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException(email));
        if(user.getVendor() != null){
            user.getVendor().getBankAccountName();
        }

        userRepository.delete(user);
    }
    
    /* Generate reports of (users,vendors,products,total values) */
    public AdminReportDto generateReport(){
        long totalUsers = userRepository.count();
        long totalVendors = vendorRepository.count();
        long totalProducts = productRepository.count();
        long totalOrders = orderRepository.count();
        long paidOrders = orderRepository.countByStatus(OrderEnum.PAID);
        long pendingOrders = orderRepository.countByStatus(OrderEnum.PENDING);

        BigDecimal totalRevenue = orderRepository.sumTotalByStatus(OrderEnum.PAID);

        return new AdminReportDto(
            totalUsers,
            totalVendors,
            totalProducts,
            totalOrders,
            paidOrders,
            pendingOrders,
            totalRevenue
        );
    }
    
}
