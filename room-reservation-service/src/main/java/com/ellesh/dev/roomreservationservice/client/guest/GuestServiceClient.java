package com.ellesh.dev.roomreservationservice.client.guest;

import com.ellesh.dev.roomreservationservice.client.guest.dto.GuestRequestDTO;
import com.ellesh.dev.roomreservationservice.client.guest.dto.GuestResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;

@Service
public class GuestServiceClient {
    private final RestTemplate restTemplate;

    //@Value("${GUEST_SERVICE_URL}")
    private String guestServiceUrl = "http://guest-service";

    private final static String GUESTS_URL_PART = "/api/v1/guests";
    private final static String SLASH = "/";

    public GuestServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<GuestResponseDTO> getGuests(String emailAddress) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(guestServiceUrl + GUESTS_URL_PART);
        if (emailAddress != null) {
            builder.queryParam("emailAddress", emailAddress);
        }
        ResponseEntity<GuestResponseDTO[]> response = this.restTemplate.getForEntity(builder.toUriString(), GuestResponseDTO[].class);
        return Arrays.asList(response.getBody());
    }

    public GuestResponseDTO getGuest(Long id) {
        String url = guestServiceUrl + GUESTS_URL_PART + SLASH + id;
        ResponseEntity<GuestResponseDTO> response = this.restTemplate.getForEntity(url, GuestResponseDTO.class);
        return response.getBody();
    }

    public GuestResponseDTO addGuest(GuestRequestDTO dto) {
        String url = guestServiceUrl + GUESTS_URL_PART;
        ResponseEntity<GuestResponseDTO> response = this.restTemplate.postForEntity(url, dto, GuestResponseDTO.class);
        return response.getBody();
    }

    public void updateGuest(Long id, GuestRequestDTO dto) {
        String url = guestServiceUrl + GUESTS_URL_PART + SLASH + id;
        this.restTemplate.put(url, dto);
    }

    public void deleteGuest(Long id) {
        String url = guestServiceUrl + GUESTS_URL_PART + SLASH + id;
        this.restTemplate.delete(url);
    }
}