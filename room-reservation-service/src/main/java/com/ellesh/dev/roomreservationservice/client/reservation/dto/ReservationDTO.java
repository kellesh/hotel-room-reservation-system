package com.ellesh.dev.roomreservationservice.client.reservation.dto;

public class ReservationDTO {
    private long reservationId;
    private long roomId;
    private long guestId;
    private String date;

    public ReservationDTO() {}

    public ReservationDTO(long reservationId, long roomId, long guestId, String date) {
        this.reservationId = reservationId;
        this.roomId = roomId;
        this.guestId = guestId;
        this.date = date;
    }

    public long getReservationId() { return reservationId; }
    public void setReservationId(long reservationId) { this.reservationId = reservationId; }
    public long getRoomId() { return roomId; }
    public void setRoomId(long roomId) { this.roomId = roomId; }
    public long getGuestId() { return guestId; }
    public void setGuestId(long guestId) { this.guestId = guestId; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
}