package com.airbnb.payload;

import com.airbnb.entity.AppUser;
import com.airbnb.entity.Property;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewsDto {

    private Long id;

    private Property property;


    private AppUser appUser;

    private String description;

    private String ratings;

}