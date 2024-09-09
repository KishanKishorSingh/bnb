package com.airbnb.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "rooms")
public class Rooms {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "price", nullable = false)
    private Float price;

    @ManyToOne
    @JoinColumn(name = "property_id")
    @JsonBackReference("Rooms")
    private Property property;

    @Column(name = "count", nullable = false)
    private Integer count;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "type_of_rooms", nullable = false)
    private String typeOfRooms;

}