package com.ellesh.dev.roomreservationservice.controllers;

import com.ellesh.dev.roomreservationservice.controllers.dto.RoomReservationDTO;
import com.ellesh.dev.roomreservationservice.models.RoomReservation;
import com.ellesh.dev.roomreservationservice.services.RoomReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/roomReservations")
public class RoomReservationController {

    private final RoomReservationService roomReservationService;

    public RoomReservationController(RoomReservationService roomReservationService) {
        this.roomReservationService = roomReservationService;
    }

    @GetMapping
    public Collection<RoomReservationDTO> getRoomReservations(@RequestParam(value = "date", required = false) String dateString) {
        return roomReservationService.getRoomReservationsForDate(dateString)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RoomReservationDTO acceptReservation(
            @RequestParam("guestId") Long guestId,
            @RequestParam("roomId") Long roomId,
            @RequestParam("date") String date) {
        RoomReservation model = roomReservationService.acceptReservation(guestId, roomId, date);
        return mapToDTO(model);
    }

    @GetMapping("/guest/{guestId}")
    public List<RoomReservationDTO> getReservationsByGuestId(
            @PathVariable("guestId") Long guestId,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate) {
        return roomReservationService.getReservationsByGuestId(guestId, startDate, endDate)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/room/{roomId}")
    public List<RoomReservationDTO> getReservationsByRoomId(
            @PathVariable("roomId") Long roomId,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate) {
        return roomReservationService.getReservationsByRoomId(roomId, startDate, endDate)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{reservationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReservation(@PathVariable("reservationId") Long reservationId) {
        roomReservationService.deleteReservation(reservationId);
    }

    private RoomReservationDTO mapToDTO(RoomReservation model) {
        RoomReservationDTO dto = new RoomReservationDTO();
        dto.setRoomId(model.getRoomId());
        dto.setRoomNumber(model.getRoomNumber());
        dto.setName(model.getName());
        dto.setBedInfo(model.getBedInfo());
        dto.setGuestId(model.getGuestId());
        dto.setFirstName(model.getFirstName());
        dto.setLastName(model.getLastName());
        dto.setReservationId(model.getReservationId());
        dto.setDate(model.getDate());
        return dto;
    }
}