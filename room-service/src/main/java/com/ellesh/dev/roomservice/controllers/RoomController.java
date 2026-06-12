package com.ellesh.dev.roomservice.controllers;

import com.ellesh.dev.roomservice.controllers.dto.RoomDTO;
import com.ellesh.dev.roomservice.models.Room;
import com.ellesh.dev.roomservice.services.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public List<RoomDTO> getAll() {
        return roomService.getAllRooms().stream()
                .map(RoomDTO::new)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RoomDTO addRoom(@RequestBody RoomDTO dto) {
        Room room = mapToEntity(dto);
        Room created = roomService.addRoom(room);
        return new RoomDTO(created);
    }

    @GetMapping("/{id}")
    public RoomDTO getRoom(@PathVariable("id") long id) {
        return new RoomDTO(roomService.getRoom(id));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateRoom(@PathVariable("id") long id, @RequestBody RoomDTO dto) {
        Room room = mapToEntity(dto);
        roomService.updateRoom(id, room);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    public void deleteRoom(@PathVariable("id") long id) {
        roomService.deleteRoom(id);
    }

    private Room mapToEntity(RoomDTO dto) {
        Room room = new Room();
        room.setRoomId(dto.getRoomId());
        room.setName(dto.getName());
        room.setRoomNumber(dto.getRoomNumber());
        room.setBedInfo(dto.getBedInfo());
        return room;
    }
}