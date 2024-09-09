package com.airbnb.service.implementationClass;


import com.airbnb.entity.City;
import com.airbnb.entity.Country;
import com.airbnb.payload.CityDto;
import com.airbnb.repository.CityRepository;
import com.airbnb.repository.CountryRepository;
import com.airbnb.service.interfaceClass.CityService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CityServiceImpl implements CityService {
    private CityRepository cityRepository;
    private CountryRepository countryRepository;

    public CityServiceImpl(CityRepository cityRepository, CountryRepository countryRepository) {
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
    }

    @Override
    public CityDto addCity(CityDto cityDto,long countryId) {
        Optional<Country> byId = countryRepository.findById(countryId);
        if (byId.isPresent()) {
            Country country = byId.get();
            City city = mapToEntity(cityDto);
            city.setCountry(country);
            City save = cityRepository.save(city);
            CityDto dto = mapToDto(save);
            return dto;
        }
        return null;
    }

    @Override
    public void deleteCity(long id) {

        cityRepository.deleteById(id);
    }

    @Override
    public CityDto updateCity(long id, CityDto cityDto) {
        Optional<City> byId = cityRepository.findById(id);
        if(byId.isPresent()){
            City city = byId.get();
            city.setCityName(cityDto.getCityName());
            City save = cityRepository.save(city);
            CityDto dto = mapToDto(save);
            return dto;
        }
        return null;
    }

    @Override
    public List<CityDto> getCity() {
        List<City> all = cityRepository.findAll();
        List<CityDto> collect = all.stream().map(this::mapToDto).collect(Collectors.toList());
        return collect;

    }

    private CityDto mapToDto(City save) {
        CityDto dto=new CityDto();
        dto.setId(save.getId());
        dto.setCityName(save.getCityName());
        dto.setCountry(save.getCountry());
        return dto;

    }

    private City mapToEntity(CityDto cityDto) {
        City city=new City();
        city.setCityName(cityDto.getCityName());
        city.setCountry(cityDto.getCountry());
        return city;
    }
}
