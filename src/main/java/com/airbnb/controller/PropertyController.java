package com.airbnb.controller;

import com.airbnb.payload.PropertyDto;
import com.airbnb.service.interfaceClass.PropertyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/Property")
public class PropertyController {
    private PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }
    //http://localhost:8080/api/v1/Property/create?country_id=&city_id=
    @PostMapping("/create")
    public ResponseEntity<PropertyDto> addProperty(@RequestParam("country_id") Long countryId,
                                                   @RequestParam("city_id") Long cityId,
                                                   @RequestBody PropertyDto dto ){
        PropertyDto dto1 = propertyService.addProperty(countryId, cityId, dto);
        return  new ResponseEntity<>(dto1, HttpStatus.CREATED);

    }
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteProperty(@RequestParam long id){
        propertyService.deleteProperty(id);
        return new ResponseEntity<>("Property Deleted Sucessfully",HttpStatus.OK);
    }
    //Search by City Name: http://localhost:8080/api/v1/Property/search?cityName=NewYork
    //Search by Country Name: http://localhost:8080/api/v1/Property/search?countryName=USA
    //Combined Search: http://localhost:8080/api/v1/Property/search?name=beach&cityName=NewYork&countryName=USA
    @GetMapping("/search")
    public ResponseEntity<List<PropertyDto>> searchProperties(
            @RequestParam(required = false) String cityName,
            @RequestParam(required = false) String countryName) {
        List<PropertyDto> properties = propertyService.searchProperties(cityName, countryName);
        return new ResponseEntity<>(properties, HttpStatus.OK);
    }
    @PutMapping("/update")
    public ResponseEntity<PropertyDto> updateProperty(@RequestParam("id") Long id,
                                                      @RequestBody PropertyDto dto) {
        PropertyDto updatedDto = propertyService.updateProperty(id,dto);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }
}
