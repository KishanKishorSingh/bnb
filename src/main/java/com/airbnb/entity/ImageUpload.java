package com.airbnb.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "image_upload")
public class ImageUpload {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "property_id")
    @JsonBackReference("ImageUpload")
    private Property property;

    @Column(name = "url", nullable = false, length = 500000)
    private String url;

}