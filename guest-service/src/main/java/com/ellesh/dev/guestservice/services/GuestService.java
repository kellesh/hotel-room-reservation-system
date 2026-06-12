package com.ellesh.dev.guestservice.services;

import com.ellesh.dev.guestservice.models.Guest;
import com.ellesh.dev.guestservice.repository.GuestRepository;
import com.ellesh.dev.guestservice.exceptions.BadReqeustException;
import com.ellesh.dev.guestservice.exceptions.NotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


public interface GuestService {

    public Iterable<Guest> getGuests(String emailAddress);

    public Guest addGuest( String firstName, String lastName, String email, String address, String country, String state, String phone_number) throws BadReqeustException;

    public Guest getGuest( Long id)  throws NotFoundException;

    public void updateGuest(Long id, String firstName, String lastName, String email, String address, String country, String state, String phone_number) throws NotFoundException;

    public void deleteGuest(Long id)  throws NotFoundException;
}
