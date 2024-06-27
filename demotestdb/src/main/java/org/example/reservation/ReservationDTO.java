package org.example.reservation;

import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record ReservationDTO(
    int id,
    int roomId,
    String uid,
    LocalTime startTime,
    LocalTime endTime,
    LocalDate reservationDate
) {
}
