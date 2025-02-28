package com.vd.tp.service;

import com.vd.tp.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.mock;

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

    }

    @Test
    public void shouldAddMember() {

    }
}
