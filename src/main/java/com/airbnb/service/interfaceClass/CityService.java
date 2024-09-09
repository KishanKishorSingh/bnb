package com.airbnb.service.interfaceClass;

import com.airbnb.payload.CityDto;

import java.util.List;

public interface CityService {
    CityDto addCity(CityDto cityDto,long countryId);

    void deleteCity(long id);

    CityDto updateCity(long id, CityDto cityDto);

    List<CityDto> getCity();
}
