package com.ellesh.dev.roomservice.repositories;

import com.ellesh.dev.roomservice.models.Room;
import org.springframework.data.repository.CrudRepository;

public interface RoomRepository extends CrudRepository<Room, Long> {

}