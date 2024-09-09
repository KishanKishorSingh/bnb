package com.airbnb.service.interfaceClass;

import com.airbnb.payload.PropertyDto;

import java.util.List;

public interface PropertyService {
    PropertyDto addProperty(long countryId, long cityId, PropertyDto dto);

    void deleteProperty(long id);

   //List<PropertyDto> searchProperties(String name, String cityName, String countryName);

    PropertyDto updateProperty(Long id, PropertyDto dto);

    List<PropertyDto> searchProperties(String cityName,String countryName);
}
