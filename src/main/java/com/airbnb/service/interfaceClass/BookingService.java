package com.airbnb.service.interfaceClass;

import com.airbnb.entity.AppUser;
import com.airbnb.entity.Bookings;

import java.util.List;

public interface BookingService {
    Bookings createBookingOfUser(Bookings bookings);

    List<Bookings> findAllByPropertyId(long propertyId);
}
