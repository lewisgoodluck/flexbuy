package com.example.flexbuy.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.flexbuy.user.model.UserPermission;


@Repository
public interface UserPermissionRepository extends JpaRepository<UserPermission,Long>{
    
}
