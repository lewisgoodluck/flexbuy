package com.example.flexbuy.user.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String businessName;

    @Column(nullable = false)
    private String businessTin;

    @Column(nullable = false)
    private String bankAccountName;

    @Column(nullable = false)
    private String bankAccountNumber;

    @Column(nullable = false)
    private String mobileVendor;

    @Column(nullable = false)
    private String mobileNumber;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonBackReference
    private User user;
}
