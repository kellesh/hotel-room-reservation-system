package com.ellesh.dev.reservationservice.services;

import com.ellesh.dev.reservationservice.models.Reservation;
import com.ellesh.dev.reservationservice.repositories.ReservationRepository;
import com.ellesh.dev.reservationservice.exceptions.BadRequestException;
import com.ellesh.dev.reservationservice.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public List<Reservation> getAllReservations() {
        List<Reservation> reservations = new ArrayList<>();
        reservationRepository.findAll().forEach(reservations::add);
        return reservations;
    }

    @Override
    public List<Reservation> getAllReservationsByGuest(Long guestId, String startDate, String endDate)
            throws BadRequestException, ParseException {
        if (guestId == null) {
            throw new BadRequestException("Guest ID must not be null");
        }
        Date start = (startDate != null) ? parseDate(startDate) : new Date(Long.MIN_VALUE);
        Date end = (endDate != null) ? parseDate(endDate) : new Date(Long.MAX_VALUE);

        List<Reservation> reservations = new ArrayList<>();
        reservationRepository.findByGuestIdAndDateBetween(guestId, start, end).forEach(reservations::add);
        return reservations;
    }

    @Override
    public List<Reservation> getAllReservationsByRoom(Long roomId, String startDate, String endDate)
            throws BadRequestException, ParseException {
        if (roomId == null) {
            throw new BadRequestException("Room ID must not be null");
        }
        Date start = (startDate != null) ? parseDate(startDate) : new Date(Long.MIN_VALUE);
        Date end = (endDate != null) ? parseDate(endDate) : new Date(Long.MAX_VALUE);

        List<Reservation> reservations = new ArrayList<>();
        reservationRepository.findByRoomIdAndDateBetween(roomId, start, end).forEach(reservations::add);
        return reservations;
    }

    @Override
    public Reservation getReservation(long id) throws NotFoundException {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Reservation not found with id: " + id));
    }

    @Override
    public Reservation createReservation(long roomId, long guestId, String date)
            throws BadRequestException {
        if (date == null) {
            throw new BadRequestException("Reservation date must not be null");
        }
        try {
            Date parsedDate = parseDate(date);
            Reservation reservation = new Reservation();
            reservation.setRoomId(roomId);
            reservation.setGuestId(guestId);
            reservation.setDate(parsedDate);
            return reservationRepository.save(reservation);
        } catch (ParseException e) {
            throw new BadRequestException("Invalid date format, expected yyyy-MM-dd");
        }
    }

    @Override
    public void updateReservation(long id, long roomId, long guestId, String date)
            throws BadRequestException, NotFoundException {
        Reservation existing = reservationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Reservation not found with id: " + id));

        if (date == null) {
            throw new BadRequestException("Reservation date must not be null");
        }
        try {
            Date parsedDate = parseDate(date);
            existing.setRoomId(roomId);
            existing.setGuestId(guestId);
            existing.setDate(parsedDate);
            reservationRepository.save(existing);
        } catch (ParseException e) {
            throw new BadRequestException("Invalid date format, expected yyyy-MM-dd");
        }
    }

    @Override
    public void deleteReservation(long id) throws NotFoundException {
        if (!reservationRepository.existsById(id)) {
            throw new NotFoundException("Reservation not found with id: " + id);
        }
        reservationRepository.deleteById(id);
    }

    private Date parseDate(String dateString) throws ParseException {
        return new Date(new SimpleDateFormat("yyyy-MM-dd").parse(dateString).getTime());
    }
}
