package com.airbnb.service.implementationClass;

import com.airbnb.entity.City;
import com.airbnb.entity.Country;
import com.airbnb.entity.Property;
import com.airbnb.exception.UserExists;
import com.airbnb.payload.PropertyDto;
import com.airbnb.repository.CityRepository;
import com.airbnb.repository.CountryRepository;
import com.airbnb.repository.PropertyRepository;
import com.airbnb.service.interfaceClass.PropertyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PropertyServicempl implements PropertyService {
    private PropertyRepository propertyRepository;
    private CountryRepository countryRepository;
    private CityRepository cityRepository;


    public PropertyServicempl(PropertyRepository propertyRepository, CountryRepository countryRepository, CityRepository cityRepository) {
        this.propertyRepository = propertyRepository;
        this.countryRepository = countryRepository;
        this.cityRepository = cityRepository;
    }

    @Override
    public PropertyDto addProperty(long countryId, long cityId, PropertyDto dto) {
        Country country = countryRepository.findById(countryId).get();
        City city = cityRepository.findById(cityId).get();
        dto.setCountry(country);
        dto.setCity(city);
        Property property = mapToEntity(dto);
        Property save = propertyRepository.save(property);
        PropertyDto dto1 = mapToDto(save);
        return dto;

    }

    @Override
    public void deleteProperty(long id) {
                propertyRepository.deleteById(id);
    }
    @Override
    public List<PropertyDto> searchProperties(String cityName, String countryName) {
        // Fetch city ID using cityName
        Long cityId =(cityName != null ? cityRepository.findByCityName(cityName).
                map(City::getId).orElse(null) : null);

        // Fetch country ID using countryName
        Long countryId = (countryName != null ? countryRepository.findByCountryName(countryName).
                map(Country::getId).orElse(null) : null);

        // Search properties by city ID and/or country ID
        List<Property> properties = propertyRepository.findByCityIdAndCountryId(cityId, countryId);
        return properties.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }


    public PropertyDto updateProperty(Long id, PropertyDto dto) {
        Optional<Property> byId = propertyRepository.findById(id);
        if (byId.isPresent()) {
            Property property = byId.get();
            property.setNumberOfGuest(dto.getNumberOfGuest());
            property.setNumberOfRooms(dto.getNumberOfRooms());
            property.setNumberOfBeds(dto.getNumberOfBeds());
            property.setNumberOfBathrooms(dto.getNumberOfBathrooms());
            Property updatedProperty = propertyRepository.save(property);
            PropertyDto dto1 = mapToDto(updatedProperty);
            return dto1;
        } else {
            throw new UserExists("Property not found with id: " + id);
        }
    }

    private PropertyDto mapToDto(Property save) {
        PropertyDto dto=new PropertyDto();
        dto.setId(save.getId());
        dto.setCity(save.getCity());
        dto.setCountry(save.getCountry());
        dto.setProperty_Name(save.getProperty_Name());
        dto.setNumberOfGuest(save.getNumberOfGuest());
        dto.setNumberOfRooms(save.getNumberOfRooms());
        dto.setNumberOfBeds(save.getNumberOfBeds());
        dto.setNumberOfBathrooms(save.getNumberOfBathrooms());
        return dto;

    }

    private Property mapToEntity(PropertyDto dto) {
        Property property=new Property();
        property.setId(dto.getId());
        property.setCity(dto.getCity());
        property.setCountry(dto.getCountry());
        property.setProperty_Name(dto.getProperty_Name());
        property.setNumberOfGuest(dto.getNumberOfGuest());
        property.setNumberOfRooms(dto.getNumberOfRooms());
        property.setNumberOfBeds(dto.getNumberOfBeds());
        property.setNumberOfBathrooms(dto.getNumberOfBathrooms());
        return property;
    }
}
