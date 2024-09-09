package com.airbnb.payload;

import com.airbnb.entity.Country;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityDto {

    private Long id;

    private String cityName;


    private Country country;


}