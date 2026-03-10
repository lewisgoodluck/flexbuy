package com.example.flexbuy.product.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.example.flexbuy.user.model.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String brand;

    private boolean isAvailable;

    private BigDecimal price;

    private int quantity;

    // RELATIONSHIPS
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;


    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ProductImage> images = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @JsonBackReference
    private Category category;

}
