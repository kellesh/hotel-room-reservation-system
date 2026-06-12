package com.ellesh.dev.guestservice.repository;

import com.ellesh.dev.guestservice.models.Guest;
import org.springframework.data.repository.CrudRepository;

public interface GuestRepository extends CrudRepository<Guest, Long> {
    Iterable<Guest> findGuestsByEmailAddress(String emailAddress);
}