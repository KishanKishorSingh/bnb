package com.airbnb.service.interfaceClass;

import com.airbnb.entity.Rooms;

import java.util.List;

public interface RoomService {
    Rooms getRoomsDetails(Rooms rooms, long propertyId);

    List<Rooms> getAllRooms();

    List<Rooms> getRoomsByPropertyId(long propertyId);

    Rooms updateRoomDetails(Rooms rooms, long id);

   void deleteRoom(long id);
}
