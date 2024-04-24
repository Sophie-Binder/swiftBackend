package org.example.room;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.NotFoundException;

import java.util.List;


@ApplicationScoped
public class RoomRepository {
    @Inject
    EntityManager entityManager;

    public Room getRoomById(int id){
        Room room = entityManager.find(Room.class, id);

        if(room == null){
            throw new NotFoundException();
        }

        return room;
    }

    public List<Room> getAllRooms(){
        return entityManager.createNamedQuery(Room.FIND_ALL_ROOMS, Room.class).getResultList();
    }

}
