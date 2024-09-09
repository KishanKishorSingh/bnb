package com.airbnb.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "city")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "country_id")
    @JsonBackReference("Country")
    private Country country;

    @OneToMany(mappedBy = "city", orphanRemoval = true)
    @JsonManagedReference("City")
    private Set<Property> properties = new LinkedHashSet<>();

    @Column(name = "city_name", nullable = false,unique = true)
    private String cityName;


}