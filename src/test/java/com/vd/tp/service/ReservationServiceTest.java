package com.vd.tp.service;

import com.vd.tp.exception.service.BadArgumentException;
import com.vd.tp.exception.service.MissingFieldsException;
import com.vd.tp.exception.service.NotFoundException;
import com.vd.tp.model.Book;
import com.vd.tp.model.Reservation;
import com.vd.tp.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {
    @Mock
    private ReservationRepository repository;

    @InjectMocks
    private ReservationService service;



    @BeforeEach
    public void setUp() {
        repository = mock(ReservationRepository.class);
        service = new ReservationService(repository);
    }

    @Test
    public void shouldSaveReservation() {
        //Given
        Reservation reservation = new Reservation();

        reservation.setReservationDate(LocalDate.now());
        reservation.setBook(new Book());

        //When
        when(repository.save(any(Reservation.class))).thenAnswer(invocation -> {
            Reservation savedReservation = invocation.getArgument(0);
            savedReservation.setId(UUID.randomUUID().toString());
            return savedReservation;
        });

        Reservation savedReservation = service.saveReservation(reservation);

        //Assert
        assertNotNull(savedReservation);
        assertNotNull(savedReservation.getId());
        assertEquals(LocalDate.now(), savedReservation.getReservationDate());

        verify(repository, times(1)).save(reservation);
    }

    @Test
    public void shouldAddReservation() {
        //Given
        Reservation reservation = new Reservation();

        reservation.setReservationDate(LocalDate.now());
        reservation.setBook(new Book());

        //When
        when(repository.save(any(Reservation.class))).thenAnswer(invocation -> {
            Reservation savedReservation = invocation.getArgument(0);
            savedReservation.setId(UUID.randomUUID().toString());
            return savedReservation;
        });

        Reservation savedReservation = service.addReservation(reservation);

        //Assert
        assertNotNull(savedReservation);
        assertNotNull(savedReservation.getId());
        assertEquals(LocalDate.now(), savedReservation.getReservationDate());

        verify(repository, times(1)).save(reservation);
    }

    @Test
    public void shouldNotAddReservationMissingData() {
        //Given
        Reservation reservation = new Reservation();

        //Assert
        assertThrows(MissingFieldsException.class, () -> service.addReservation(reservation));

        verify(repository, times(0)).save(reservation);
    }

    @Test
    public void shouldCalculateReservationDueDate() {
        //Given
        Reservation reservation = new Reservation();

        reservation.setReservationDate(LocalDate.now());
        reservation.setBook(new Book());

        //When
        when(repository.save(any(Reservation.class))).thenAnswer(invocation -> {
            Reservation savedReservation = invocation.getArgument(0);
            savedReservation.setId(UUID.randomUUID().toString());
            return savedReservation;
        });

        Reservation savedReservation = service.addReservation(reservation);

        //Assert
        assertNotNull(savedReservation);
        assertNotNull(savedReservation.getId());
        assertEquals(LocalDate.now(), savedReservation.getReservationDate());
        assertEquals(LocalDate.now().plusMonths(4), savedReservation.getDueDate());

        verify(repository, times(1)).save(reservation);
    }

    @Test
    public void shouldCloseReservation() {
        //Given
        Reservation reservation = new Reservation();
        reservation.setId(UUID.randomUUID().toString());

        //When
        when(repository.save(any(Reservation.class))).thenAnswer(invocation -> {
            Reservation savedReservation = invocation.getArgument(0);
            savedReservation.setId(UUID.randomUUID().toString());
            return savedReservation;
        });


        when(repository.existsById(reservation.getId())).thenReturn(true);

        Reservation savedReservation = service.closeReservation(reservation);

        //Assert
        assertNotNull(savedReservation);
        assertNotNull(savedReservation.getId());
        assertTrue(reservation.isClosed());

        verify(repository, times(1)).save(reservation);
    }

    @Test
    public void shouldNotCloseReservationIDNotFound() {
        //Given
        Reservation reservation = new Reservation();
        reservation.setId(UUID.randomUUID().toString());

        //When
        when(repository.existsById(reservation.getId())).thenReturn(false);

        //Assert
        assertThrows(NotFoundException.class, () -> service.closeReservation(reservation));

        verify(repository, never()).save(reservation);
    }

    @Test
    public void shouldNotCloseReservationAlreadyClose() {
        //Given
        Reservation reservation = new Reservation();
        reservation.setId(UUID.randomUUID().toString());
        reservation.setClosed(true);

        //When
        when(repository.existsById(reservation.getId())).thenReturn(true);

        //Assert
        assertThrows(BadArgumentException.class, () -> service.closeReservation(reservation));

        verify(repository, never()).save(reservation);
    }


    @Test
    public void shouldDeleteReservation() {
        //Given
        Reservation reservation = new Reservation();
        reservation.setId(UUID.randomUUID().toString());

        //When
        when(repository.existsById(reservation.getId())).thenReturn(true);

        //Assert
        service.deleteReservation(reservation);

        verify(repository, times(1)).deleteById(reservation.getId());
    }

    @Test
    public void shouldNotDeleteBecauseNotFound() {
        //Given
        Reservation reservation = new Reservation();
        reservation.setId(UUID.randomUUID().toString());

        //When
        when(repository.existsById(reservation.getId())).thenReturn(false);

        //Assert
        assertThrows(NotFoundException.class, () -> service.deleteReservation(reservation));

        verify(repository, never()).deleteById(reservation.getId());
    }
}