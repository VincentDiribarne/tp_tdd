package com.vd.tp.service;

import com.vd.tp.exception.service.BadArgumentException;
import com.vd.tp.exception.service.MissingFieldsException;
import com.vd.tp.exception.service.NotFoundException;
import com.vd.tp.model.Book;
import com.vd.tp.model.Member;
import com.vd.tp.model.Reservation;
import com.vd.tp.model.enums.Civility;
import com.vd.tp.repository.MemberRepository;
import com.vd.tp.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {
    @Mock
    private MemberRepository repository;

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private MemberService service;

    @InjectMocks
    private ReservationService reservationService;

    @BeforeEach
    public void setUp() {
        repository = mock(MemberRepository.class);
        reservationRepository = mock(ReservationRepository.class);

        reservationService = new ReservationService(reservationRepository);
        service = new MemberService(repository, reservationService);
    }

    /* Save */
    @Test
    public void shouldSaveMember() {
        //Given
        Member member = new Member();

        member.setFirstName("Vincent");
        member.setLastName("DIRIBARNE");
        member.setEmail("vdiribarne@gmail.com");
        member.setMemberCode("12347547");
        member.setBirthDate(LocalDate.of(2003, 1, 5));
        member.setCivility(Civility.MR);

        //Should
        when(repository.save(any(Member.class))).thenAnswer(invocation -> {
            Member memberSaved = invocation.getArgument(0);
            memberSaved.setId(UUID.randomUUID().toString());
            return memberSaved;
        });

        Member memberSaved = service.saveMember(member);

        // Assert
        assertNotNull(memberSaved);
        assertNotNull(memberSaved.getId());
        assertEquals("Vincent", memberSaved.getFirstName());
        verify(repository, times(1)).save(memberSaved);
    }

    @Test
    public void shouldAddMember() {
        //Given
        Member member = new Member();

        member.setFirstName("Vincent");
        member.setLastName("DIRIBARNE");
        member.setEmail("vdiribarne@gmail.com");
        member.setMemberCode("12347547");
        member.setBirthDate(LocalDate.of(2003, 1, 5));
        member.setCivility(Civility.MR);

        //Should
        when(repository.save(any(Member.class))).thenAnswer(invocation -> {
            Member memberSaved = invocation.getArgument(0);
            memberSaved.setId(UUID.randomUUID().toString());
            return memberSaved;
        });

        Member memberSaved = service.addMember(member);

        // Assert
        assertNotNull(memberSaved);
        assertNotNull(memberSaved.getId());
        assertEquals("Vincent", memberSaved.getFirstName());
        verify(repository, times(1)).save(memberSaved);
    }

    @Test
    public void shouldNotCreateMemberWithMissingData() {
        //Given
        Member member = new Member();
        member.setFirstName("Vincent");
        member.setLastName("DIRIBARNE");
        member.setMemberCode("1478");
        member.setCivility(Civility.MR);

        // Assert
        assertThrows(MissingFieldsException.class, () -> service.addMember(member));
        verify(repository, never()).save(any(Member.class));
    }

    @Test
    public void shouldUpdateMember() {
        //Given
        Member member = new Member();

        member.setFirstName("Toto");
        member.setLastName("DIRIBARNE");
        member.setEmail("vdiribarne@gmail.com");
        member.setMemberCode("12347547");

        //Should
        when(repository.existsById(anyString())).thenReturn(true);

        when(repository.save(any(Member.class))).thenAnswer(invocation -> {
            Member savedMember = invocation.getArgument(0);
            savedMember.setId(UUID.randomUUID().toString());
            return savedMember;
        });

        Member memberSaved = service.updateMember(UUID.randomUUID().toString(), member);

        // Assert
        assertNotNull(memberSaved);
        assertNotNull(memberSaved.getId());
        assertEquals("Toto", memberSaved.getFirstName());
        verify(repository, times(1)).save(memberSaved);
    }

    @Test
    public void shouldNotUpdateMember() {
        //Given
        Member member = new Member();

        member.setFirstName("Toto");
        member.setLastName("DIRIBARNE");
        member.setEmail("vdiribarne@gmail.com");
        member.setMemberCode("12347547");

        //Should
        when(repository.existsById(anyString())).thenReturn(false);

        // Assert
        assertThrows(NotFoundException.class, () -> service.updateMember(UUID.randomUUID().toString(), member));
        verify(repository, times(1)).existsById(anyString());
        verify(repository, never()).save(member);
    }


    /* Delete */
    @Test
    public void shouldDeleteBookByIdWhenExists() {
        // Given
        String id = UUID.randomUUID().toString();
        when(repository.existsById(id)).thenReturn(true);

        // When
        service.deleteById(id);

        // Then
        verify(repository, times(1)).existsById(id);
        verify(repository, times(1)).deleteById(id);
    }


    @Test
    public void shouldThrowExceptionWhenBookNotFound() {
        // Given
        String id = UUID.randomUUID().toString();
        when(repository.existsById(id)).thenReturn(false);

        // When & Then
        assertThrows(NotFoundException.class, () -> service.deleteById(id));

        verify(repository, times(1)).existsById(id);
        verify(repository, never()).deleteById(any());
    }

    @Test
    public void shouldAddReservation() {
        //Given
        Member member = new Member();
        member.setEmail("vdiribarne@gmail.com");
        member.setMemberCode("12347547");

        Reservation reservation = new Reservation();
        reservation.setReservationDate(LocalDate.now());
        reservation.setBook(new Book());

        //Should
        when(repository.save(any(Member.class))).thenAnswer(invocation -> {
            Member memberSaved = invocation.getArgument(0);
            memberSaved.setId(UUID.randomUUID().toString());
            return memberSaved;
        });

        when(reservationRepository.save(any(Reservation.class))).thenAnswer(invocation -> {
            Reservation savedReservation = invocation.getArgument(0);
            savedReservation.setId(UUID.randomUUID().toString());
            return savedReservation;
        });

        when(repository.existsById(any())).thenReturn(true);

        Member memberSaved = service.addReservation(member, reservation);

        //Assert
        assertNotNull(memberSaved);
        assertEquals(1, memberSaved.getReservations().size());
        assertEquals(LocalDate.now(), memberSaved.getReservations().getFirst().getReservationDate());
    }

    @Test
    public void shouldNotAddReservationBecauseMemberNotFound() {
        //Given
        Member member = new Member();
        member.setEmail("vdiribarne@gmail.com");
        member.setMemberCode("1234");

        Reservation reservation = new Reservation();
        reservation.setReservationDate(LocalDate.now());
        reservation.setBook(new Book());

        //Assert

        assertThrows(NotFoundException.class, () -> service.addReservation(member, reservation));

        verify(repository, never()).save(any(Member.class));
    }

    @Test
    public void shouldNotAddReservationBecauseMoreThan3() {
        // Given
        Member member = new Member();
        member.setEmail("vdiribarne@gmail.com");
        member.setMemberCode("1234");
        member.setReservations(new ArrayList<>(List.of(new Reservation(), new Reservation(), new Reservation())));

        Reservation reservation = new Reservation();
        reservation.setReservationDate(LocalDate.now());
        reservation.setBook(new Book());

        when(repository.existsById(any())).thenReturn(true);

        MemberService spyService = spy(service);
        when(spyService.tooManyReservations(member)).thenReturn(true);

        //Assert
        assertThrows(BadArgumentException.class, () -> spyService.addReservation(member, reservation));

        verify(repository, never()).save(any(Member.class));
    }
}