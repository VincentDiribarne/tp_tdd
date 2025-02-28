package com.vd.tp.service;

import com.vd.tp.exception.service.MissingFieldsException;
import com.vd.tp.model.Reservation;
import com.vd.tp.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository repository;

    public Reservation saveReservation(Reservation reservation) {
        return repository.save(reservation);
    }

    public Reservation addReservation(Reservation reservation) {
        if (reservation.getReservationDate() == null) throw new MissingFieldsException("Reservation date is required");

        calculateDueDate(reservation);

        return saveReservation(reservation);
    }

    public Reservation closeReservation(Reservation reservation) {
        //if (repository.existsById(reservation.getId())) throw new MissingFieldsException("Reservation does not exist");

        reservation.setClosed(true);

        return saveReservation(reservation);
    }

    private void calculateDueDate(Reservation reservation) {
        reservation.setDueDate(reservation.getReservationDate().plusMonths(4));
    }
}
