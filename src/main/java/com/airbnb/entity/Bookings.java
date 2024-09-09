package com.airbnb.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "bookings")
public class Bookings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "guest_email", nullable = false)
    private String guestEmail;

    @ManyToOne
    @JoinColumn(name = "app_user_id")
    @JsonBackReference("booking")
    private AppUser appUser;

    @ManyToOne
    @JoinColumn(name = "property_id")
    @JsonBackReference("UserBooking")
    private Property property;

    @Column(name = "check_out_date", nullable = false)
    private LocalDate checkOutDate;

    @Column(name = "number_of_rooms", nullable = false)
    private Integer numberOfRooms;

    @Column(name = "check_in_date", nullable = false)
    private LocalDate checkInDate;

    @Column(name = "guest_mobile", nullable = false, length = 10)
    private String guestMobile;

    @Column(name = "guest_name", nullable = false)
    private String guestName;

    @Column(name = "total_price")
    private Float totalPrice;

    @Column(name = "types_of_rooms", nullable = false)
    private String typesOfRooms;

    @Column(name = "number_of_nights", nullable = false)
    private Integer numberOfNights;

}