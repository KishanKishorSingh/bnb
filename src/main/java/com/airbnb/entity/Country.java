package com.airbnb.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "country")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "country_name", nullable = false,unique = true)
    private String countryName;

    @OneToMany(mappedBy = "country", orphanRemoval = true)
    @JsonManagedReference("Country")
    private Set<City> cities = new LinkedHashSet<>();

    @OneToMany(mappedBy = "country", orphanRemoval = true)
    @JsonManagedReference("Country")
    private Set<Property> properties = new LinkedHashSet<>();

}