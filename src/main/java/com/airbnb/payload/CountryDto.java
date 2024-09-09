package com.airbnb.payload;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CountryDto {

    private Long id;
    private String countryName;

}