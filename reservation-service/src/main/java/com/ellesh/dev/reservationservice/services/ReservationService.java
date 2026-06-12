package com.ellesh.dev.reservationservice.services;
import com.ellesh.dev.reservationservice.models.Reservation;
import com.ellesh.dev.reservationservice.exceptions.BadRequestException;
import com.ellesh.dev.reservationservice.exceptions.NotFoundException;

import java.text.ParseException;
import java.util.List;

public interface ReservationService {

    List<Reservation> getAllReservations();

    List<Reservation> getAllReservationsByGuest(Long guestId, String startDate, String endDate)
            throws BadRequestException, ParseException;

    List<Reservation> getAllReservationsByRoom(Long roomId, String startDate, String endDate)
            throws BadRequestException, ParseException;

    Reservation getReservation(long id) throws NotFoundException;

    Reservation createReservation(long roomId, long guestId, String date)
            throws BadRequestException;

    void updateReservation(long id, long roomId, long guestId, String date)
            throws BadRequestException, NotFoundException;

    void deleteReservation(long id) throws NotFoundException;
}
