package com.example.flexbuy.user.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "permissions", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id","role_id"}))
@Entity
public class UserPermission{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    @JsonBackReference
    private Role role;

}
