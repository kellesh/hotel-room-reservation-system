package com.ellesh.dev.roomreservationservice.controllers;

import com.ellesh.dev.roomreservationservice.models.RoomReservation;
import com.ellesh.dev.roomreservationservice.services.RoomReservationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.hamcrest.Matchers.containsString;


@WebMvcTest(RoomReservationController.class)
public class RoomReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoomReservationService roomReservationService;

    @Test
    public void getRoomReservations() throws Exception {
        List<RoomReservation> reservations = new ArrayList<>();
        given(roomReservationService.getRoomReservationsForDate("2023-01-01")).willReturn(reservations);

        this.mockMvc.perform(get("/api/v1/roomReservations?date=2023-01-01"))
                .andExpect(status().isOk());
    }
}