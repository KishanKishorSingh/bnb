package com.airbnb.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;



import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "app_user")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToMany(mappedBy = "appUser", orphanRemoval = true,fetch = FetchType.EAGER)
    @JsonManagedReference("Appuser")
    private Set<Reviews> reviewses = new LinkedHashSet<>();

    @Column(name = "role",nullable = false, length = 100)
    private String role;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "appUser", orphanRemoval = true)
    @JsonManagedReference("booking")
    private Set<Bookings> bookingses = new LinkedHashSet<>();

    @Column(name = "username", nullable = false, unique = true, length = 100)
    private String username;

    @Column(name = "password", nullable = false,length=10000)
    @JsonIgnore
    private String password;

}