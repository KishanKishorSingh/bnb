package com.airbnb.controller;


import com.airbnb.payload.CountryDto;
import com.airbnb.service.interfaceClass.CountryService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/country")
public class CountryController {
    private CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }
    @PostMapping("/create")
    public ResponseEntity<CountryDto> addCountry(@RequestBody CountryDto countryDto){
        CountryDto dto = countryService.addCountry(countryDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
    @PatchMapping("/update")
    public ResponseEntity<CountryDto> updateCity(@RequestParam long id, @RequestBody CountryDto countryDto){
        CountryDto dto = countryService.updateCountry(id, countryDto);
        return new ResponseEntity<>(dto,HttpStatus.ACCEPTED);
    }
    @GetMapping("/List")
    public ResponseEntity<List<CountryDto>> getAllCityName(){
        List<CountryDto> country = countryService.getCountry();
        return new ResponseEntity<>(country,HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    @Transactional
    public ResponseEntity<String> deleteCountry(@RequestParam long id){
        countryService.deleteCountry(id);
        return new ResponseEntity<>("Country Deleted Sucessfully",HttpStatus.OK);
    }

}
