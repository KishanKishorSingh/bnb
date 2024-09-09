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
@Table(name = "property")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "property_name", nullable = false,unique = true)
    private String property_Name;

    @Column(name = "number_of_guest", nullable = false)
    private Integer numberOfGuest;

    @Column(name = "number_of_rooms", nullable = false)
    private Integer numberOfRooms;

    @Column(name = "number_of_beds", nullable = false)
    private Integer numberOfBeds;

    @Column(name = "number_of_bathrooms", nullable = false)
    private Integer numberOfBathrooms;

    @OneToMany(mappedBy = "property", orphanRemoval = true)
    @JsonManagedReference("Rooms")
    private Set<Rooms> roomses = new LinkedHashSet<>();

    @OneToMany(mappedBy = "property", orphanRemoval = true)
    @JsonManagedReference("UserBooking")
    private Set<Bookings> bookingses = new LinkedHashSet<>();

    @OneToMany(mappedBy = "property", orphanRemoval = true)
    @JsonManagedReference("ImageUpload")
    private Set<ImageUpload> imageUploads = new LinkedHashSet<>();

    @OneToMany(mappedBy = "property", orphanRemoval = true)
    @JsonManagedReference("Property")
    private Set<Reviews> reviewses = new LinkedHashSet<>();

    @ManyToOne
    @JoinColumn(name = "country_id")
    @JsonBackReference("Country")
    private Country country;

    @ManyToOne
    @JoinColumn(name = "city_id")
    @JsonBackReference("City")
    private City city;

}