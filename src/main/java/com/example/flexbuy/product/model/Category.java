package com.example.flexbuy.product.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Category{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String categoryName;

    @Column(columnDefinition = "TEXT")
    private String categoryDescription;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @JsonBackReference
    private Category parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Category> subCategories = new HashSet<>();

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Product> products = new HashSet<>();
}
