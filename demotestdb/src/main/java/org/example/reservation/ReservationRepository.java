package org.example.reservation;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import java.util.concurrent.ExecutionException;



@ApplicationScoped
public class ReservationRepository {

    @Inject
    EntityManager entityManager;

    public Reservation findByIdReservation(int id){
        Reservation reservation = entityManager.find(Reservation.class, id);
        if (reservation == null){
            throw new NotFoundException();
        }
        return reservation;
    }

    public List<Reservation> getAllReservations(){
        return entityManager.createNamedQuery(Reservation.FIND_ALL_RESERVATIONS, Reservation.class).getResultList();
    }

        public List<Reservation> getReservationsByRoom(int roomId){
            TypedQuery<Reservation> query = entityManager.createNamedQuery(Reservation.FIND_RESERVATIONS_BY_ROOM, Reservation.class);
            query.setParameter("roomId", roomId);
            return query.getResultList();
        }

    public List<Reservation> getReservationsByPerson(int personId){
        TypedQuery<Reservation> query = entityManager.createNamedQuery(Reservation.FIND_RESERVATIONS_BY_PERSON, Reservation.class);
        query.setParameter("personId", personId);
        return query.getResultList();
    }

    @Transactional
    public void addReservation(Reservation reservation){
       if (!checkReservation(reservation)) {
         throw new BadRequestException();
       }
       entityManager.persist(reservation);
    }

    @Transactional
    public void deleteReservation(int id){
        Reservation reservation = findByIdReservation(id);
        entityManager.remove(reservation);
    }

    @Transactional
    public void updateReservation(int id, Reservation reservation){
        Reservation oldReservation = findByIdReservation(id);
        oldReservation.setReservationDate(reservation.getReservationDate());
        oldReservation.setUid(reservation.getUid());
        oldReservation.setRoom(reservation.getRoom());
        oldReservation.setEndTime(reservation.getEndTime());
        oldReservation.setStartTime(reservation.getStartTime());
    }

    public boolean checkReservation(Reservation reservation){

        boolean result = true;

        for (Reservation currRes: getAllReservations()) {
            //checks if reservation is between another reservation
            if (reservation.getRoom().getId() == currRes.getRoom().getId()) {

                if (reservation.getStartTime().isAfter(currRes.getStartTime()) && reservation.getStartTime().isBefore(currRes.getEndTime())) {
                    result =  false;
                } else if (reservation.getEndTime().isAfter(currRes.getStartTime()) && reservation.getEndTime().isBefore(currRes.getEndTime())) {
                    result =  false;
                } else if (currRes.getStartTime().isAfter(reservation.getStartTime()) && currRes.getStartTime().isBefore(reservation.getEndTime())) {
                    result =  false;
                } else if (currRes.getEndTime().isAfter(reservation.getStartTime()) && currRes.getEndTime().isBefore(reservation.getEndTime())) {
                    result =  false;
                } else if (reservation.getEndTime().isEqual(currRes.getEndTime()) || reservation.getStartTime().isEqual(currRes.getStartTime())) {
                    result = false;
                }
            }
        }
            return result;
    }

    public List<Reservation> getWeeklyReservations(String weekDay){

        List<Reservation> reservations = new LinkedList<>();

        for (int i = 0; i < 5; i++) {
            String dt = weekDay;  // Start date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            try {
                c.setTime(sdf.parse(dt));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            c.add(Calendar.DATE, i);  // number of days to add
            dt = sdf.format(c.getTime());

            for (Reservation currRes: getAllReservations()) {
                if (currRes.getReservationDate().toString().equals(dt)){
                    reservations.add(currRes);
                }
            }

        }
        return reservations;
    }




}