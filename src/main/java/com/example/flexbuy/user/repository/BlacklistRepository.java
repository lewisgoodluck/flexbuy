package com.example.flexbuy.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.flexbuy.user.model.Blacklist;

public interface BlacklistRepository extends JpaRepository<Blacklist,Long>{
    boolean existsByJti(String jti);
}
