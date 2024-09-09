package com.airbnb.controller;

import com.airbnb.entity.AppUser;
import com.airbnb.entity.Bookings;
import com.airbnb.entity.Property;
import com.airbnb.entity.Rooms;
import com.airbnb.repository.BookingsRepository;
import com.airbnb.repository.PropertyRepository;
import com.airbnb.repository.RoomsRepository;
import com.airbnb.service.interfaceClass.BookingService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1/booking")
public class BookingController {
    private BookingService bookingService;
    private RoomsRepository roomsRepository;
    private PropertyRepository propertyRepository;

    public BookingController(BookingService bookingService,RoomsRepository roomsRepository, PropertyRepository propertyRepository) {
        this.bookingService = bookingService;
        this.roomsRepository = roomsRepository;
        this.propertyRepository = propertyRepository;
    }
    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody Bookings bookings,
                                           @RequestParam long propertyId,
                                           @RequestParam  LocalDate checkinDate,
                                           @RequestParam LocalDate checkoutDate,
                                           @AuthenticationPrincipal AppUser user){

        Optional<Property> byId = propertyRepository.findById(propertyId);
        if(byId.isPresent()){
            Property property = byId.get();
           List<LocalDate> datesBetween= findDateBetween(checkinDate,checkoutDate);
           List<Rooms> room=new ArrayList<>();
           for(LocalDate date:datesBetween) {
               Rooms byTypeOfRooms = roomsRepository.findByTypeOfRoomsDateAndPropertyId(bookings.getTypesOfRooms(), date, propertyId);
               if (byTypeOfRooms.getCount() == 0) {
                   return new ResponseEntity<>("No Rooms Available", HttpStatus.UNAUTHORIZED);
               }
               room.add(byTypeOfRooms);
               Integer count = byTypeOfRooms.getCount();
               if(Objects.equals(bookings.getTypesOfRooms(), byTypeOfRooms.getTypeOfRooms())){
                Float price = byTypeOfRooms.getPrice();
                if(count > 0) {
                    if (checkinDate.isBefore(checkoutDate) || checkinDate.isEqual(checkoutDate)) {
                        long days = ChronoUnit.DAYS.between(checkinDate, checkoutDate);
                        bookings.setTotalPrice(days * price * bookings.getNumberOfRooms());
                        byTypeOfRooms.setCount(count - bookings.getNumberOfRooms());
                        //Integer count1 = byTypeOfRooms.getCount();
                        bookings.setCheckInDate(checkinDate);
                        bookings.setNumberOfNights((int) days);
                        bookings.setCheckOutDate(checkoutDate);
                        bookings.setProperty(property);
                        bookings.setAppUser(user);
                        Bookings bookingOfUser = bookingService.createBookingOfUser(bookings);
                        return new ResponseEntity<>(bookingOfUser, HttpStatus.CREATED);
                    }
                }else{
                        return new ResponseEntity<>("check In Date should before Check Out Date",HttpStatus.BAD_REQUEST);
                    }
                }else {
                    return  new ResponseEntity<>("Room Fully booked",HttpStatus.NOT_FOUND);
                }
            } else {
                return  new ResponseEntity<>("Room type not available",HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("Property not found", HttpStatus.NOT_FOUND);
        }
    }

    private List<LocalDate> findDateBetween(LocalDate checkinDate, LocalDate checkoutDate) {
        List<LocalDate> datesBetween = new ArrayList<>();

        while (!checkinDate.isAfter(checkoutDate)) {
            datesBetween.add(checkinDate);
            checkinDate = checkinDate.plusDays(1);
        }
        return datesBetween;
    }
}
