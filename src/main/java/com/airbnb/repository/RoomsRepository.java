package com.airbnb.repository;

import com.airbnb.entity.Property;
import com.airbnb.entity.Rooms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RoomsRepository extends JpaRepository<Rooms, Long> {
   List<Rooms> findByPropertyId(long propertyId);
  Rooms findByTypeOfRoomsDateAndPropertyId(String typeOfRooms, LocalDate date, long propertyId);
    @Query("SELECT r FROM Rooms r WHERE r.id NOT IN (" +
            "SELECT b.room.id FROM Bookings b WHERE b.checkInDate <= :checkOutDate AND b.checkOutDate >= :checkInDate)")
    List<Rooms> findAvailableRoomsBetweenDates(@Param("checkInDate") LocalDate checkInDate,
                                               @Param("checkOutDate") LocalDate checkOutDate);
}