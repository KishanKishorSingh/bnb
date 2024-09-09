package com.airbnb.payload;

import com.airbnb.entity.City;
import com.airbnb.entity.Country;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PropertyDto {

    private Long id;

    private String property_Name;

    private Integer numberOfGuest;

    private Integer numberOfRooms;

    private Integer numberOfBeds;

    private Integer numberOfBathrooms;


    private Country country;


    private City city;

}