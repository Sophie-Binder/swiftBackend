package org.example.reservation;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.example.person.Person;
import org.example.person.PersonRepository;
import org.example.room.Room;
import org.example.room.RoomRepository;

import java.time.LocalDateTime;

@ApplicationScoped
public class ReservationMapper {
    @Inject
    PersonRepository personRepository;

    @Inject
    RoomRepository roomRepository;

    public ReservationDTO toDTO(Reservation reservation){
        Room room = reservation.getRoom();

        return new ReservationDTO(reservation.getId(),room.getId() ,reservation.getUid(), reservation.getStartTime().toLocalTime(), reservation.getEndTime().toLocalTime(), reservation.getReservationDate());
    }

    public Reservation toEntity(ReservationDTO reservationDTO){
        Room room = roomRepository.getRoomById(reservationDTO.roomId());
        return new Reservation(reservationDTO.id(), room, reservationDTO.uid(), LocalDateTime.of(reservationDTO.reservationDate(),reservationDTO.startTime()),LocalDateTime.of(reservationDTO.reservationDate(),reservationDTO.endTime()),reservationDTO.reservationDate());
    }

}
