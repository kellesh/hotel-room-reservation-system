package com.ellesh.dev.roomreservationservice.client.room;

import com.ellesh.dev.roomreservationservice.client.room.dto.RoomDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class RoomServiceClient {

    private final RestTemplate restTemplate;

    //@Value("${ROOM_SERVICE_URL}")
    private String roomServiceUrl="http://room-service";

    private final static String ROOMS_URL_PART = "api/v1/rooms";
    private final static String SLASH = "/";

    public RoomServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<RoomDTO> getAll() {
        String url = roomServiceUrl + ROOMS_URL_PART;
        ResponseEntity<RoomDTO[]> response = this.restTemplate.getForEntity(url, RoomDTO[].class);
        return Arrays.asList(response.getBody());
    }

    public RoomDTO addRoom(RoomDTO dto) {
        String url = roomServiceUrl + ROOMS_URL_PART;
        ResponseEntity<RoomDTO> response = this.restTemplate.postForEntity(url, dto, RoomDTO.class);
        return response.getBody();
    }

    public RoomDTO getRoom(long id) {
        String url = roomServiceUrl + ROOMS_URL_PART + SLASH + id;
        ResponseEntity<RoomDTO> response = this.restTemplate.getForEntity(url, RoomDTO.class);
        return response.getBody();
    }

    public void updateRoom(long id, RoomDTO dto) {
        String url = roomServiceUrl + ROOMS_URL_PART + SLASH + id;
        this.restTemplate.put(url, dto);
    }

    public void deleteRoom(long id) {
        String url = roomServiceUrl + ROOMS_URL_PART + SLASH + id;
        this.restTemplate.delete(url);
    }
}