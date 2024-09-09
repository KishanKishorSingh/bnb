package com.airbnb.service.implementationClass;

import com.airbnb.entity.AppUser;
import com.airbnb.entity.Bookings;
import com.airbnb.repository.BookingsRepository;
import com.airbnb.repository.PropertyRepository;
import com.airbnb.repository.RoomsRepository;
import com.airbnb.service.interfaceClass.BookingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {
    private BookingsRepository bookingsRepository;


    public BookingServiceImpl(BookingsRepository bookingsRepository) {
        this.bookingsRepository = bookingsRepository;
    }

    @Override
    public Bookings createBookingOfUser(Bookings bookings) {

        return bookingsRepository.save(bookings);
    }

    @Override
    public List<Bookings> findAllByPropertyId(long propertyId) {
        List<Bookings> allByPropertyId = bookingsRepository.findAllByPropertyId(propertyId);
        return allByPropertyId;
    }
}





