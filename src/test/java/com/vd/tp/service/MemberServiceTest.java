package com.vd.tp.service;

import com.vd.tp.exception.service.MissingFieldsException;
import com.vd.tp.model.Member;
import com.vd.tp.model.enums.Civility;
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

        //Should
        when(repository.save(any(Member.class))).thenAnswer(invocation -> {
            Member memberSaved = invocation.getArgument(0);
            memberSaved.setId(UUID.randomUUID().toString());
            return memberSaved;
        });

        // Assert
        assertThrows(MissingFieldsException.class, () -> service.addMember(member));
        verify(repository, never()).save(any(Member.class));
    }
}
