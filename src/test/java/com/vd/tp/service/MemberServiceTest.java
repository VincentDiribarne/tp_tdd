package com.vd.tp.service;

import com.vd.tp.exception.service.MissingFieldsException;
import com.vd.tp.exception.service.NotFoundException;
import com.vd.tp.model.Book;
import com.vd.tp.model.Member;
import com.vd.tp.model.enums.Civility;
import com.vd.tp.model.enums.Format;
import com.vd.tp.repository.MemberRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {
    @Mock
    private MemberRepository repository;

    @InjectMocks
    private MemberService service;

    @BeforeEach
    public void setUp() {
        repository = mock(MemberRepository.class);
        service = new MemberService(repository);
    }

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
}
