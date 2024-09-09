package com.airbnb.service.implementationClass;

import com.airbnb.entity.Country;
import com.airbnb.payload.CountryDto;
import com.airbnb.repository.CountryRepository;
import com.airbnb.service.interfaceClass.CountryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CountryServiceImpl implements CountryService {
 private CountryRepository countryRepository;

    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public CountryDto addCountry(CountryDto countryDto) {
        Country country = mapToEntity(countryDto);
        Country save = countryRepository.save(country);
        CountryDto dto = mapToDto(save);
        return dto;



    }

    @Override
    public void deleteCountry(long id) {
        countryRepository.deleteById(id);
    }

    @Override
    public CountryDto updateCountry(long id, CountryDto countryDto) {
        Optional<Country> byId = countryRepository.findById(id);
        if(byId.isPresent()){
            Country country = byId.get();
            country.setCountryName(countryDto.getCountryName());
            Country save = countryRepository.save(country);
            CountryDto dto = mapToDto(save);
            return dto;
        }
        return null;
    }

    @Override
    public List<CountryDto> getCountry() {
        List<Country> all = countryRepository.findAll();
        List<CountryDto> collect = all.stream().map(this::mapToDto).collect(Collectors.toList());
        return collect;
    }


    private CountryDto mapToDto(Country save) {
        CountryDto dto=new CountryDto();
        dto.setId(save.getId());
        dto.setCountryName(save.getCountryName());
        return dto;
    }

    private Country mapToEntity(CountryDto countryDto) {
        Country country=new Country();
        country.setCountryName(countryDto.getCountryName());
        return country;
    }
}
