package com.ellesh.dev.reservationservice.repositories;

import com.ellesh.dev.reservationservice.models.Reservation;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;

public interface ReservationRepository extends CrudRepository<Reservation, Long> {
    Iterable<Reservation> findAll();

    Iterable<Reservation> findByGuestId(Long guestId);

    Iterable<Reservation> findByDate(Date date);

    Iterable<Reservation> findByRoomId(Long roomId);

    Iterable<Reservation> findByDateAndGuestId(Date date, Long guestId);

    Iterable<Reservation> findByRoomIdAndDateBetween(Long roomId, Date start, Date end);

    Iterable<Reservation> findByGuestIdAndDateBetween(Long guestId, Date start, Date end);
}