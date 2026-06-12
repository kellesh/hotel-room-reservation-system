package com.ellesh.dev.roomservice.services;

import com.ellesh.dev.roomservice.models.Room;
import com.ellesh.dev.roomservice.repositories.RoomRepository;
import com.ellesh.dev.roomservice.exceptions.BadRequestException;
import com.ellesh.dev.roomservice.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public List<Room> getAllRooms() {
        return StreamSupport.stream(roomRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Room getRoom(long id) {
        Optional<Room> room = this.roomRepository.findById(id);
        if (room.isEmpty()) {
            throw new NotFoundException("id not found " + id);
        }
        return room.get();
    }

    @Override
    public Room addRoom(Room room) {
        return this.roomRepository.save(room);
    }

    @Override
    public void updateRoom(long id, Room room) {
        if (id != room.getRoomId()) {
            throw new BadRequestException("id in body doesn't match path");
        }
        Optional<Room> existing = this.roomRepository.findById(id);
        if (existing.isEmpty()) {
            throw new NotFoundException("id not found " + id);
        }
        this.roomRepository.save(room);
    }

    @Override
    public void deleteRoom(long id) {
        this.roomRepository.deleteById(id);
    }
}