package com.airbnb.repository;

import com.airbnb.entity.Bookings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingsRepository extends JpaRepository<Bookings, Long> {
    List<Bookings> findAllByPropertyId(long propertyId);
}