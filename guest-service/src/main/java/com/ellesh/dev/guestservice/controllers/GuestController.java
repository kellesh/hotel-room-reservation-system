package com.ellesh.dev.guestservice.controllers;

import com.ellesh.dev.guestservice.controllers.dto.GuestRequestDTO;
import com.ellesh.dev.guestservice.controllers.dto.GuestResponseDTO;
import com.ellesh.dev.guestservice.models.Guest;
import com.ellesh.dev.guestservice.services.GuestService;
import com.ellesh.dev.guestservice.exceptions.NotFoundException;
import com.ellesh.dev.guestservice.exceptions.BadReqeustException;

import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;
import java.util.List;

@RestController
@RequestMapping("/api/guests")
public class GuestController {

    private final GuestService guestService;

    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }

    @GetMapping
    public List<GuestResponseDTO> getGuests(@RequestParam(value = "emailAddress", required = false) String emailAddress) {
        return ((List<Guest>) guestService.getGuests(emailAddress))
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public GuestResponseDTO getGuest(@PathVariable Long id) throws NotFoundException {
        return mapToResponse(guestService.getGuest(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GuestResponseDTO addGuest(@Valid @RequestBody GuestRequestDTO dto) {
        Guest guest = guestService.addGuest(
                dto.getFirstName(),
                dto.getLastName(),
                dto.getEmailAddress(),
                dto.getAddress(),
                dto.getCountry(),
                dto.getState(),
                dto.getPhoneNumber()
        );
        return mapToResponse(guest);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateGuest(@PathVariable Long id, @Valid @RequestBody GuestRequestDTO dto) throws NotFoundException {
        guestService.updateGuest(
                id,
                dto.getFirstName(),
                dto.getLastName(),
                dto.getEmailAddress(),
                dto.getAddress(),
                dto.getCountry(),
                dto.getState(),
                dto.getPhoneNumber()
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGuest(@PathVariable Long id) throws NotFoundException {
        guestService.deleteGuest(id);
    }

    private GuestResponseDTO mapToResponse(Guest guest) {
        return new GuestResponseDTO(
                guest.getGuestId(),
                guest.getFirstName(),
                guest.getLastName(),
                guest.getEmailAddress(),
                guest.getAddress(),
                guest.getCountry(),
                guest.getState(),
                guest.getPhoneNumber()
        );
    }
}