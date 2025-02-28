package com.vd.tp.service;

import com.vd.tp.exception.service.BadArgumentException;
import com.vd.tp.exception.service.MissingFieldsException;
import com.vd.tp.exception.service.NotFoundException;
import com.vd.tp.model.Reservation;
import com.vd.tp.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository repository;
    private final MailService mailService;

    public boolean existByID(String id) {
        return repository.existsById(id);
    }

    public List<Reservation> findAllReservations() {
        return repository.findAll();
    }

    public Reservation saveReservation(Reservation reservation) {
        return repository.save(reservation);
    }

    public Reservation addReservation(Reservation reservation) {
        if (reservation.getReservationDate() == null) throw new MissingFieldsException("Reservation date is required");

        if (reservation.getBook() == null) throw new NotFoundException("Book not found");

        calculateDueDate(reservation);

        return saveReservation(reservation);
    }

    public Reservation closeReservation(Reservation reservation) {
        if (!repository.existsById(reservation.getId())) throw new NotFoundException("Reservation not found");

        if (reservation.isClosed()) throw new BadArgumentException("Reservation is already closed");


        reservation.setClosed(true);
        return saveReservation(reservation);
    }

    private void calculateDueDate(Reservation reservation) {
        reservation.setDueDate(reservation.getReservationDate().plusMonths(4));
    }

    public void deleteReservation(Reservation reservation) {
        if (!repository.existsById(reservation.getId())) throw new NotFoundException("Reservation not found");

        repository.deleteById(reservation.getId());
    }

    public void sendMailDueDate(Reservation reservation) {
        if (!repository.existsById(reservation.getId())) throw new NotFoundException("Reservation not found");

        mailService.sendMail("email");
    }
}
