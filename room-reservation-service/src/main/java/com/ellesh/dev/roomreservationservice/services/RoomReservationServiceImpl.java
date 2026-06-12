package com.ellesh.dev.roomreservationservice.services;

import com.ellesh.dev.roomreservationservice.client.guest.dto.GuestResponseDTO;
import com.ellesh.dev.roomreservationservice.client.guest.GuestServiceClient;
import com.ellesh.dev.roomreservationservice.client.reservation.dto.ReservationDTO;
import com.ellesh.dev.roomreservationservice.client.reservation.ReservationServiceClient;
import com.ellesh.dev.roomreservationservice.client.room.dto.RoomDTO;
import com.ellesh.dev.roomreservationservice.client.room.RoomServiceClient;
import com.ellesh.dev.roomreservationservice.models.RoomReservation;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoomReservationServiceImpl implements RoomReservationService {

    private final GuestServiceClient guestServiceClient;
    private final ReservationServiceClient reservationServiceClient;
    private final RoomServiceClient roomServiceClient;

    public RoomReservationServiceImpl(GuestServiceClient guestServiceClient, ReservationServiceClient reservationServiceClient, RoomServiceClient roomServiceClient) {
        this.guestServiceClient = guestServiceClient;
        this.reservationServiceClient = reservationServiceClient;
        this.roomServiceClient = roomServiceClient;
    }

    @Override
    public Collection<RoomReservation> getRoomReservationsForDate(String dateString) {
        if (!StringUtils.hasLength(dateString)) {
            Date date = new Date(System.currentTimeMillis());
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateString = dateFormat.format(date);
        }
        final String usableDateString = dateString;
        
        List<RoomDTO> rooms = this.roomServiceClient.getAll();
        
        Map<Long, RoomReservation> roomReservations = new HashMap<>(rooms.size());
        rooms.forEach(room -> {
            RoomReservation roomReservation = new RoomReservation();
            roomReservation.setRoomId(room.getRoomId());
            roomReservation.setRoomNumber(room.getRoomNumber());
            roomReservation.setBedInfo(room.getBedInfo());
            roomReservation.setName(room.getName());
            roomReservation.setDate(usableDateString);
            roomReservations.put(room.getRoomId(), roomReservation);
        });
        
        List<ReservationDTO> reservations = this.reservationServiceClient.getAllReservations();
        
        reservations.stream()
                .filter(r -> usableDateString.equals(r.getDate()))
                .forEach(reservation -> {
            RoomReservation roomReservation = roomReservations.get(reservation.getRoomId());
            if (roomReservation != null) {
                roomReservation.setReservationId(reservation.getReservationId());
                roomReservation.setGuestId(reservation.getGuestId());

                try {
                    GuestResponseDTO guest = this.guestServiceClient.getGuest(roomReservation.getGuestId());
                    roomReservation.setFirstName(guest.getFirstName());
                    roomReservation.setLastName(guest.getLastName());
                } catch (Exception e) {
                    // Guest not found or error, ignore
                }
            }
        });
        return roomReservations.values();
    }

    @Override
    public RoomReservation acceptReservation(Long guestId, Long roomId, String date) {
        GuestResponseDTO guest;
        try {
            guest = guestServiceClient.getGuest(guestId);
        } catch (Exception e) {
            throw new RuntimeException("Guest not found with ID: " + guestId);
        }

        RoomDTO room;
        try {
            room = roomServiceClient.getRoom(roomId);
        } catch (Exception e) {
            throw new RuntimeException("Room not found with ID: " + roomId);
        }

        List<ReservationDTO> existingReservations = reservationServiceClient.getReservationsByRoom(roomId, date, date);
        boolean isReserved = !existingReservations.isEmpty();

        if (isReserved) {
            throw new RuntimeException("Room is already reserved for the given date: " + date);
        }

        ReservationDTO reservation = new ReservationDTO();
        reservation.setGuestId(guestId);
        reservation.setRoomId(roomId);
        reservation.setDate(date);

        ReservationDTO createdReservation = reservationServiceClient.createReservation(reservation);

        return buildRoomReservation(createdReservation, guest, room);
    }

    @Override
    public List<RoomReservation> getReservationsByGuestId(Long guestId, String startDate, String endDate) {
        List<ReservationDTO> reservations = reservationServiceClient.getReservationsByGuest(guestId, startDate, endDate);

        return reservations.stream()
                .map(this::buildRoomReservation)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoomReservation> getReservationsByRoomId(Long roomId, String startDate, String endDate) {
        List<ReservationDTO> reservations = reservationServiceClient.getReservationsByRoom(roomId, startDate, endDate);

        return reservations.stream()
                .map(this::buildRoomReservation)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteReservation(Long reservationId) {
        reservationServiceClient.deleteReservation(reservationId);
    }

    private RoomReservation buildRoomReservation(ReservationDTO reservation) {
        GuestResponseDTO guest = null;
        try {
            guest = guestServiceClient.getGuest(reservation.getGuestId());
        } catch (Exception e) {
            // Ignore
        }

        RoomDTO room = null;
        try {
            room = roomServiceClient.getRoom(reservation.getRoomId());
        } catch (Exception e) {
            // Ignore
        }

        return buildRoomReservation(reservation, guest, room);
    }

    private RoomReservation buildRoomReservation(ReservationDTO reservation, GuestResponseDTO guest, RoomDTO room) {
        RoomReservation model = new RoomReservation();
        model.setReservationId(reservation.getReservationId());
        model.setDate(reservation.getDate());
        
        if (guest != null) {
            model.setGuestId(guest.getId());
            model.setFirstName(guest.getFirstName());
            model.setLastName(guest.getLastName());
        } else {
            model.setGuestId(reservation.getGuestId());
        }

        if (room != null) {
            model.setRoomId(room.getRoomId());
            model.setRoomNumber(room.getRoomNumber());
            model.setName(room.getName());
            model.setBedInfo(room.getBedInfo());
        } else {
            model.setRoomId(reservation.getRoomId());
        }

        return model;
    }
}