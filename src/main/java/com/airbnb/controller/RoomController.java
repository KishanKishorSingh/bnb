package com.airbnb.controller;

import com.airbnb.entity.Rooms;
import com.airbnb.service.interfaceClass.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
public class RoomController {
    private RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }
    @PostMapping("/create")
    public ResponseEntity<?> createRooms(@RequestBody Rooms rooms, @RequestParam long propertyId){
        Rooms roomsDetails = roomService.getRoomsDetails(rooms, propertyId);
        if(roomsDetails!=null){
            return new ResponseEntity<>(roomsDetails, HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity<>("property not found",HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/getAllRooms")
    public ResponseEntity<List<Rooms>> getAllRooms(){
        List<Rooms> allRooms = roomService.getAllRooms();
        return new ResponseEntity<>(allRooms,HttpStatus.OK);
    }
    @GetMapping("/getRoomsByPropertyId")
    public ResponseEntity<?> getRoomsByPropertyId(@RequestParam long propertyId){
        List<Rooms> roomsByPropertyId = roomService.getRoomsByPropertyId(propertyId);
        if (!roomsByPropertyId.isEmpty()) {
            return new ResponseEntity<>(roomsByPropertyId, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Rooms not found in property",HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/update")
    public ResponseEntity<?> updateRoomDetails(@RequestBody Rooms rooms,@RequestParam long id){
        Rooms rooms1 = roomService.updateRoomDetails(rooms, id);
        if(rooms1!=null){
            return new ResponseEntity<>(rooms1,HttpStatus.ACCEPTED);
        }
        else{
            return new ResponseEntity<>("Not Found",HttpStatus.UNAUTHORIZED);
        }
    }
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteRoom(@RequestParam long id){
        roomService.deleteRoom(id);
        return new ResponseEntity<>("Deleted Successfully",HttpStatus.OK);
    }
}

