package com.airbnb.repository;

import com.airbnb.entity.Rooms;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface RoomsRepository extends JpaRepository<Rooms, Long> {
   List<Rooms> findByPropertyId(long propertyId);
  Rooms findByTypeOfRoomsAndDateAndPropertyId(String typeOfRooms, LocalDate date, long propertyId);
}