package com.ellesh.dev.roomreservationservice.services;

import com.ellesh.dev.roomreservationservice.models.RoomReservation;

import java.util.Collection;
import java.util.List;

public interface RoomReservationService {
    Collection<RoomReservation> getRoomReservationsForDate(String dateString);
    RoomReservation acceptReservation(Long guestId, Long roomId, String date);
    List<RoomReservation> getReservationsByGuestId(Long guestId, String startDate, String endDate);
    List<RoomReservation> getReservationsByRoomId(Long roomId, String startDate, String endDate);
    void deleteReservation(Long reservationId);
}