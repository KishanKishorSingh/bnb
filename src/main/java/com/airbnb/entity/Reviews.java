package com.airbnb.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "reviews")
public class Reviews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "property_id")
    @JsonBackReference("Property")
    private Property property;

    @ManyToOne
    @JoinColumn(name = "app_user_id")
    @JsonBackReference("Appuser")
    private AppUser appUser;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "ratings", nullable = false)
    private String ratings;

}