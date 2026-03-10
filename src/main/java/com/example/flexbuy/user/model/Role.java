package com.example.flexbuy.user.model;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Table(name = "roles")
@Entity
public class Role implements GrantedAuthority{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Override
    public String getAuthority(){
        return name;
    }

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<UserPermission> permissions = new HashSet<>();
}
