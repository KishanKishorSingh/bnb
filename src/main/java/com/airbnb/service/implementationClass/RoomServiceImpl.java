package com.airbnb.service.implementationClass;

import com.airbnb.entity.Property;
import com.airbnb.entity.Rooms;
import com.airbnb.repository.PropertyRepository;
import com.airbnb.repository.RoomsRepository;
import com.airbnb.service.interfaceClass.RoomService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {
    private RoomsRepository roomsRepository;
    private PropertyRepository propertyRepository;

    public RoomServiceImpl(RoomsRepository roomsRepository, PropertyRepository propertyRepository) {
        this.roomsRepository = roomsRepository;
        this.propertyRepository = propertyRepository;
    }

    @Override
    public Rooms getRoomsDetails(Rooms rooms, long propertyId) {
        Optional<Property> byId = propertyRepository.findById(propertyId);
        if(byId.isPresent()){
            Property property = byId.get();
            rooms.setProperty(property);
            Rooms save = roomsRepository.save(rooms);
            return save;
        }
        return null;
    }

    @Override
    public List<Rooms> getAllRooms() {
        return roomsRepository.findAll();
    }

    @Override
    public List<Rooms> getRoomsByPropertyId(long propertyId) {
        List<Rooms> byPropertyId = roomsRepository.findByPropertyId(propertyId);
        return byPropertyId;
    }

    @Override
    public Rooms updateRoomDetails(Rooms rooms, long id) {
        Optional<Rooms> byId = roomsRepository.findById(id);
       if(byId.isPresent()){
           Rooms rooms1 = byId.get();
           rooms1.setCount(rooms.getCount());
           rooms1.setPrice(rooms.getPrice());
           return rooms1;
       }
       return null;
    }

    @Override
    public  void deleteRoom(long id) {
        Optional<Rooms> byId = roomsRepository.findById(id);
        if (byId.isPresent()) {
            roomsRepository.deleteById(id);
        } else {
            throw new RuntimeException("Room not found with id: " + id);
        }
    }
}
