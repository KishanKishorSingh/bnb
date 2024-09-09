package com.airbnb.controller;

import com.airbnb.payload.CityDto;
import com.airbnb.service.interfaceClass.CityService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/City")
public class CityController {
    private CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }
    @PostMapping("/create")
    public ResponseEntity<CityDto> addCity(@RequestBody CityDto cityDto,@RequestParam("country_id") long countryId){
        CityDto dto = cityService.addCity(cityDto,countryId);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
    @PatchMapping("/update")
    public ResponseEntity<CityDto> updateCity(@RequestParam long id, @RequestBody CityDto cityDto){
        CityDto dto = cityService.updateCity(id, cityDto);
        return new ResponseEntity<>(dto,HttpStatus.ACCEPTED);
    }
    @GetMapping("/List")
    public ResponseEntity<List<CityDto>> getAllCityName(){
        List<CityDto> city = cityService.getCity();
        return new ResponseEntity<>(city,HttpStatus.OK);
    }
    @DeleteMapping("/delete")
    @Transactional
    public ResponseEntity<String> deleteCity(@RequestParam long id){
        cityService.deleteCity(id);
        return new ResponseEntity<>("Delete Sucessfully",HttpStatus.OK);
    }
            }
