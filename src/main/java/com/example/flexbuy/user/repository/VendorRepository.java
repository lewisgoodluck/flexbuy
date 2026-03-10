package com.example.flexbuy.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.flexbuy.user.model.User;
import com.example.flexbuy.user.model.Vendor;


public interface VendorRepository extends JpaRepository<Vendor,Long>{
    Optional<Vendor> findByUser(User user);
}
