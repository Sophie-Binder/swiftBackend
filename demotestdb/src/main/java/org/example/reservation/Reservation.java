package org.example.reservation;

import org.example.person.Person;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.example.room.Room;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;


@Entity
@NamedQuery(name = Reservation.FIND_ALL_RESERVATIONS, query = "Select r from Reservation r")
@NamedQuery(name = Reservation.FIND_RESERVATIONS_BY_ROOM, query = "SELECT r from Reservation r where r.room.id = :roomId")
@NamedQuery(name = Reservation.FIND_RESERVATIONS_BY_PERSON, query = "SELECT r from Reservation r where r.person.id = :personId")
public class Reservation {

    public static final String FIND_ALL_RESERVATIONS = "Reservation.findAll";
    public static final String FIND_RESERVATIONS_BY_ROOM = "Reservation.filterByRoom";

    public static final String FIND_RESERVATIONS_BY_PERSON = "Reservation.filterByPerson";

    @Id
    @SequenceGenerator(name = "reservation_seq", sequenceName = "reservation_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reservation_seq")
    private int id;
    private static int countId;
    @ManyToOne
    @JoinColumn(name = "roomId")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "personId")
    private Person person;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDate reservationDate;

    public Reservation(Room room, Person person, LocalDateTime startTime, LocalDateTime endTime, LocalDate date) {
        this.room = room;
        this.person = person;
        this.startTime = startTime;
        this.endTime = endTime;
        this.reservationDate = date;
    }

    public Reservation(int id,Room room, Person person, LocalDateTime startTime, LocalDateTime endTime, LocalDate date) {
        this(room,person,startTime,endTime,date);
        this.id = id;
    }


    public Reservation() {
    }

    public int getId() {
        return id;
    }



    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDate date) {
        this.reservationDate = date;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", roomId=" + room +
                ", personId=" + person +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", reservationDate=" + reservationDate +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(room, that.room) && Objects.equals(startTime, that.startTime) && Objects.equals(endTime, that.endTime) && Objects.equals(reservationDate, that.reservationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(room, startTime, endTime, reservationDate);
    }
}
