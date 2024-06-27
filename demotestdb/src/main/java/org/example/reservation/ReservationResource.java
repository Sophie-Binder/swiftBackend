package org.example.reservation;


import com.google.api.client.json.webtoken.JsonWebToken;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import org.example.person.PersonRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;


@Path("/api/reservations")
@RequestScoped
public class ReservationResource {
    @Inject
    ReservationRepository reservationRepository;

    @Inject
    PersonRepository personRepository;
    @Inject
    ReservationMapper reservationMapper;

    //@Inject
    //JsonWebToken jwt;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/list")
    public List<ReservationDTO> reservationList() {
        //personRepository.addPerson(jwt.getSubject(),jwt.getClaim(Claims.family_name).toString(),jwt.getClaim(Claims.given_name).toString(),jwt.getClaim(Claims.email).toString());
        return this.reservationRepository.getAllReservations().stream().map(reservationMapper::toDTO).toList();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public ReservationDTO reservationById(@PathParam("id") int id){
        return reservationMapper.toDTO(this.reservationRepository.findByIdReservation(id));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/room/{id}")
    public List<ReservationDTO> reservationByRoom(@PathParam("id") int id){
        return reservationRepository.getReservationsByRoom(id).stream().map(reservationMapper::toDTO).toList();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/person/{id}")
    public List<ReservationDTO> reservationByPerson(@PathParam("id") int id){
        return reservationRepository.getReservationsByPerson(id).stream().map(reservationMapper::toDTO).toList();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void addReservation(ReservationDTO reservationDTO, @Context ContainerRequestContext requestContext){
        this.reservationRepository.addReservation(reservationMapper.toEntity(reservationDTO));

    }

    @DELETE
    //@Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public void deleteReservation(@PathParam("id") int id){
        this.reservationRepository.deleteReservation(id);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    //@RolesAllowed({"admin"})
    @PermitAll
    public void updateReservation(@PathParam("id") int id, ReservationDTO reservationDTO){
        this.reservationRepository.updateReservation(id,reservationMapper.toEntity(reservationDTO));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/week/{weekDay}")
    public List<ReservationDTO> getWeekReservation(@PathParam("weekDay") String weekDay){
        return this.reservationRepository.getWeeklyReservations(weekDay).stream().map(reservationMapper::toDTO).toList();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/weekDay/{weekDay}")
    public List<String> getWeek(@PathParam("weekDay") String weekDay) {
        List<String> dates = new LinkedList<>();

        for (int i = 0; i < 5; i++) {
            String dt = weekDay;  // Start date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            try {
                c.setTime(sdf.parse(dt));
            } catch (
                    ParseException e) {
                throw new RuntimeException(e);
            }
            c.add(Calendar.DATE, i);  // number of days to add
            dt = sdf.format(c.getTime());
            dates.add(dt);
        }
        return dates;
    }


}

