package com.ellesh.dev.guestservice.services;

import com.ellesh.dev.guestservice.models.Guest;
import com.ellesh.dev.guestservice.repository.GuestRepository;
import com.ellesh.dev.guestservice.exceptions.BadReqeustException;
import com.ellesh.dev.guestservice.exceptions.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class GuestServiceImpl implements GuestService {

    private final GuestRepository guestRepository;

    public GuestServiceImpl(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    @Override
    public Iterable<Guest> getGuests(String emailAddress) {
        if (StringUtils.hasText(emailAddress)) {
            return guestRepository.findGuestsByEmailAddress(emailAddress);
        }
        return guestRepository.findAll();
    }

    @Override
    public Guest addGuest(String firstName, String lastName, String email,
                          String address, String country, String state, String phoneNumber)
            throws BadReqeustException {

        if (!StringUtils.hasText(firstName) || !StringUtils.hasText(lastName) || !StringUtils.hasText(email)) {
            throw new BadReqeustException("First name, last name, and email are required");
        }

        Guest guest = new Guest();
        guest.setFirstName(firstName);
        guest.setLastName(lastName);
        guest.setEmailAddress(email);
        guest.setAddress(address);
        guest.setCountry(country);
        guest.setState(state);
        guest.setPhoneNumber(phoneNumber);

        return guestRepository.save(guest);
    }

    @Override
    public Guest getGuest(Long id) throws NotFoundException {
        Optional<Guest> guestOpt = guestRepository.findById(id);
        if (guestOpt.isEmpty()) {
            throw new NotFoundException("Guest not found with id: " + id);
        }
        return guestOpt.get();
    }

    @Override
    public void updateGuest(Long id, String firstName, String lastName, String email,
                            String address, String country, String state, String phoneNumber)
            throws NotFoundException {

        Guest guest = guestRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Guest not found with id: " + id));

        if (StringUtils.hasText(firstName)) guest.setFirstName(firstName);
        if (StringUtils.hasText(lastName)) guest.setLastName(lastName);
        if (StringUtils.hasText(email)) guest.setEmailAddress(email);
        if (StringUtils.hasText(address)) guest.setAddress(address);
        if (StringUtils.hasText(country)) guest.setCountry(country);
        if (StringUtils.hasText(state)) guest.setState(state);
        if (StringUtils.hasText(phoneNumber)) guest.setPhoneNumber(phoneNumber);

        guestRepository.save(guest);
    }

    @Override
    public void deleteGuest(Long id) throws NotFoundException {
        Guest guest = guestRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Guest not found with id: " + id));
        guestRepository.delete(guest);
    }
}
