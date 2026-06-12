package com.ellesh.dev.roomservice.services;

import com.ellesh.dev.roomservice.models.Room;
import java.util.List;

public interface RoomService {
    List<Room> getAllRooms();
    Room getRoom(long id);
    Room addRoom(Room room);
    void updateRoom(long id, Room room);
    void deleteRoom(long id);
}