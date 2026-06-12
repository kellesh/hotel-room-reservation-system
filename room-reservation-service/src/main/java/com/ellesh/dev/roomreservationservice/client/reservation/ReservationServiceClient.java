package com.ellesh.dev.roomreservationservice.client.reservation;

import com.ellesh.dev.roomreservationservice.client.reservation.dto.ReservationDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;

@Service
public class ReservationServiceClient {
    private final RestTemplate restTemplate;

    @Value("${RESERVATION_SERVICE_URL}")
    private String reservationServiceUrl;

    private final static String RESERVATIONS_URL_PART = "/reservations";
    private final static String SLASH = "/";

    public ReservationServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<ReservationDTO> getAllReservations() {
        String url = reservationServiceUrl + RESERVATIONS_URL_PART;
        ResponseEntity<ReservationDTO[]> response = this.restTemplate.getForEntity(url, ReservationDTO[].class);
        return Arrays.asList(response.getBody());
    }

    public List<ReservationDTO> getReservationsByGuest(Long guestId, String startDate, String endDate) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(reservationServiceUrl + RESERVATIONS_URL_PART + "/guest/" + guestId);
        if (startDate != null) builder.queryParam("startDate", startDate);
        if (endDate != null) builder.queryParam("endDate", endDate);
        
        ResponseEntity<ReservationDTO[]> response = this.restTemplate.getForEntity(builder.toUriString(), ReservationDTO[].class);
        return Arrays.asList(response.getBody());
    }

    public List<ReservationDTO> getReservationsByRoom(Long roomId, String startDate, String endDate) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(reservationServiceUrl + RESERVATIONS_URL_PART + "/room/" + roomId);
        if (startDate != null) builder.queryParam("startDate", startDate);
        if (endDate != null) builder.queryParam("endDate", endDate);
        
        ResponseEntity<ReservationDTO[]> response = this.restTemplate.getForEntity(builder.toUriString(), ReservationDTO[].class);
        return Arrays.asList(response.getBody());
    }

    public ReservationDTO getReservation(long id) {
        String url = reservationServiceUrl + RESERVATIONS_URL_PART + SLASH + id;
        ResponseEntity<ReservationDTO> response = this.restTemplate.getForEntity(url, ReservationDTO.class);
        return response.getBody();
    }

    public ReservationDTO createReservation(ReservationDTO dto) {
        String url = reservationServiceUrl + RESERVATIONS_URL_PART;
        ResponseEntity<ReservationDTO> response = this.restTemplate.postForEntity(url, dto, ReservationDTO.class);
        return response.getBody();
    }

    public void updateReservation(long id, ReservationDTO dto) {
        String url = reservationServiceUrl + RESERVATIONS_URL_PART + SLASH + id;
        this.restTemplate.put(url, dto);
    }

    public void deleteReservation(long id) {
        String url = reservationServiceUrl + RESERVATIONS_URL_PART + SLASH + id;
        this.restTemplate.delete(url);
    }
}