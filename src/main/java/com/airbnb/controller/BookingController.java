package com.airbnb.controller;

import com.airbnb.entity.AppUser;
import com.airbnb.entity.Bookings;
import com.airbnb.entity.Property;
import com.airbnb.entity.Rooms;
import com.airbnb.repository.PropertyRepository;
import com.airbnb.repository.RoomsRepository;
import com.airbnb.service.interfaceClass.BookingService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@RestController
@RequestMapping("/api/v1/booking")
public class BookingController {
    private BookingService bookingService;
    private RoomsRepository roomsRepository;
    private PropertyRepository propertyRepository;

    public BookingController(BookingService bookingService, RoomsRepository roomsRepository, PropertyRepository propertyRepository) {
        this.bookingService = bookingService;
        this.roomsRepository = roomsRepository;
        this.propertyRepository = propertyRepository;
    }

    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody Bookings bookings,
                                           @RequestParam long propertyId,
                                           @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkinDate,
                                           @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkoutDate,
                                           @AuthenticationPrincipal AppUser user) {

        Optional<Property> byId = propertyRepository.findById(propertyId);
        Float total = 0f;
        if (byId.isPresent()) {
            Property property = byId.get();
            List<LocalDate> datesBetween = findDateBetween(checkinDate, checkoutDate);
            List<Rooms> room = new ArrayList<>();
            for (LocalDate date : datesBetween) {

                Rooms byTypeOfRooms = roomsRepository.findByTypeOfRoomsAndDateAndPropertyId(bookings.getTypesOfRooms(), date, propertyId);
                if (byTypeOfRooms.getCount()< bookings.getNumberOfRooms()) {
                    room.removeAll(room);
                    return new ResponseEntity<>("No Rooms Available", HttpStatus.UNAUTHORIZED);
                }
                byTypeOfRooms.setCount(byTypeOfRooms.getCount()-bookings.getNumberOfRooms());

                room.add(byTypeOfRooms);
                total = total + (byTypeOfRooms.getPrice()*bookings.getNumberOfRooms());
            }
                if (checkinDate.isBefore(checkoutDate) || checkinDate.isEqual(checkoutDate)) {
                    long days = ChronoUnit.DAYS.between(checkinDate, checkoutDate);
                    bookings.setTotalPrice(total);
                    bookings.setCheckInDate(checkinDate);
                    bookings.setNumberOfNights((int) days);
                    bookings.setCheckOutDate(checkoutDate);
                    bookings.setProperty(property);
                    bookings.setAppUser(user);
                    Bookings bookingOfUser = bookingService.createBookingOfUser(bookings);
                    return new ResponseEntity<>(bookingOfUser, HttpStatus.CREATED);

                }




        }
        return new ResponseEntity<>("some error occurs",HttpStatus.BAD_REQUEST);
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

