package org.example.room;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
import java.util.Map;

@Path("/api/rooms")
public class RoomResource {

    @Inject
    RoomRepository roomRepository;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/list")
    public List<Room> getAllRooms(){
        return roomRepository.getAllRooms();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Room getRoom(@PathParam("id") int id){
        return roomRepository.getRoomById(id);
    }

}
