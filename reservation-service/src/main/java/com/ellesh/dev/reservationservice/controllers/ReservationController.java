package com.ellesh.dev.reservationservice.controllers;

import com.ellesh.dev.reservationservice.controllers.dto.ReservationDTO;
import com.ellesh.dev.reservationservice.models.Reservation;
import com.ellesh.dev.reservationservice.services.ReservationService;
import com.ellesh.dev.reservationservice.exceptions.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public List<ReservationDTO> getAllReservations() {
        return reservationService.getAllReservations()
                .stream()
                .map(ReservationDTO::new)
                .collect(Collectors.toList());
    }

    // Fetch reservations by guest with optional date range
    @GetMapping("/guest/{guestId}")
    public List<ReservationDTO> getReservationsByGuest(
            @PathVariable("guestId") Long guestId,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate) throws ParseException {
        return reservationService.getAllReservationsByGuest(guestId, startDate, endDate)
                .stream()
                .map(ReservationDTO::new)
                .collect(Collectors.toList());
    }

    // Fetch reservations by room with optional date range
    @GetMapping("/room/{roomId}")
    public List<ReservationDTO> getReservationsByRoom(
            @PathVariable("roomId") Long roomId,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate) throws ParseException {
        return reservationService.getAllReservationsByRoom(roomId, startDate, endDate)
                .stream()
                .map(ReservationDTO::new)
                .collect(Collectors.toList());
    }

    // Get single reservation
    @GetMapping("/{id}")
    public ReservationDTO getReservation(@PathVariable("id") long id) {
        Reservation reservation = reservationService.getReservation(id);
        return new ReservationDTO(reservation);
    }

    // Create reservation using primitive attributes from DTO
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationDTO createReservation(@RequestBody ReservationDTO dto) {
        if (dto.getDate() == null) {
            throw new BadRequestException("Date must not be null");
        }
        Reservation created = reservationService.createReservation(
                dto.getRoomId(),
                dto.getGuestId(),
                dto.getDate()
        );
        return new ReservationDTO(created);
    }

    // Update reservation using primitive attributes from DTO
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateReservation(@PathVariable("id") long id, @RequestBody ReservationDTO dto) {
        if (dto.getDate() == null) {
            throw new BadRequestException("Date must not be null");
        }
        reservationService.updateReservation(
                id,
                dto.getRoomId(),
                dto.getGuestId(),
                dto.getDate()
        );
    }

    // Delete reservation
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    public void deleteReservation(@PathVariable("id") long id) {
        reservationService.deleteReservation(id);
    }
}
