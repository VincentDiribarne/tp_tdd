package com.vd.tp.service;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
        service.saveReservation(reservation);

        //Assert
        assertNotNull(reservation);
        assertNotNull(reservation.getId());
        assertEquals(LocalDate.now(), reservation.getReservationDate());

        verify(repository, times(1)).save(reservation);
    }

}
