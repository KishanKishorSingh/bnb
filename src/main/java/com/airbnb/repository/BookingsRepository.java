package com.airbnb.repository;

import com.airbnb.entity.Bookings;
import com.airbnb.entity.Rooms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BookingsRepository extends JpaRepository<Bookings, Long> {
    List<Bookings> findAllByPropertyId(long propertyId);
//    
//    @Query("SELECT r FROM Rooms r WHERE r.id NOT IN (" +
//            "SELECT b.room.id FROM Bookings b WHERE " +
//            "b.checkInDate <= :checkOutDate AND b.checkOutDate >= :checkInDate)")
//    List<Rooms> findAvailableRoomsBetweenDates(@Param("checkInDate") LocalDate checkInDate,
//                                               @Param("checkOutDate") LocalDate checkOutDate);
}