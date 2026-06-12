package com.ellesh.dev.roomreservationservice.client.room.dto;

public class RoomDTO {
    private long roomId;
    private String name;
    private String roomNumber;
    private String bedInfo;

    public RoomDTO() {}

    public RoomDTO(long roomId, String name, String roomNumber, String bedInfo) {
        this.roomId = roomId;
        this.name = name;
        this.roomNumber = roomNumber;
        this.bedInfo = bedInfo;
    }

    public long getRoomId() { return roomId; }
    public void setRoomId(long roomId) { this.roomId = roomId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }
    public String getBedInfo() { return bedInfo; }
    public void setBedInfo(String bedInfo) { this.bedInfo = bedInfo; }
}