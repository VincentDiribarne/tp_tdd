package com.vd.tp.service;

import com.vd.tp.exception.service.BadArgumentException;
import com.vd.tp.exception.service.MissingFieldsException;
import com.vd.tp.exception.service.NotFoundException;
import com.vd.tp.model.Book;
import com.vd.tp.model.enums.Format;
import com.vd.tp.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    private BookRepository repository;

    @InjectMocks
    private BookService service;

    @BeforeEach
    void setUp() {
        repository = mock(BookRepository.class);
        service = new BookService(repository);
    }

    /* FIND */
    @Test
    public void shouldFindByISBN() {
        //Given
        Book book = new Book("978-1917067287", "The Adventures of Sherlock Holmes", "Arthur Conan Doyle", "Nielsen UK", Format.ROMAN, true);

        //Should
        when(repository.findByIsbn(anyString())).thenReturn(Optional.of(book));

        Book foundBook = service.findBookByISBN("978-1917067287");

        // Assert
        assertNotNull(foundBook);
        assertEquals("The Adventures of Sherlock Holmes", foundBook.getTitle());
        verify(repository, times(1)).findByIsbn("978-1917067287");
    }

    @Test
    public void shouldNotFindByISBN() {
        //Given

        //Should
        when(repository.findByIsbn(anyString())).thenReturn(Optional.empty());

        // Assert
        assertThrows(NotFoundException.class, () -> service.findBookByISBN("978-1917067287"));
    }


    /* ADD */
    @Test
    public void shouldSaveBookSuccessfully() {
        //Given
        Book book = new Book("978-1917067287", "The Adventures of Sherlock Holmes", "Arthur Conan Doyle", "Nielsen UK", Format.ROMAN, true);

        //Should
        when(repository.save(any(Book.class))).thenAnswer(invocation -> {
            Book savedBook = invocation.getArgument(0);
            savedBook.setId(UUID.randomUUID().toString());
            return savedBook;
        });

        Book savedBook = service.saveBook(book);

        // Assert
        assertNotNull(savedBook);
        assertNotNull(savedBook.getId());
        assertEquals("The Adventures of Sherlock Holmes", savedBook.getTitle());
        verify(repository, times(1)).save(book);
    }

    @Test
    public void shouldAddBookSuccessfully() {
        //Given
        Book book = new Book("978-1917067287", "The Adventures of Sherlock Holmes", "Arthur Conan Doyle", "Nielsen UK", Format.ROMAN, true);

        //Should
        when(repository.save(any(Book.class))).thenAnswer(invocation -> {
            Book savedBook = invocation.getArgument(0);
            savedBook.setId(UUID.randomUUID().toString());
            return savedBook;
        });

        Book savedBook = service.addBook(book);

        // Assert
        assertNotNull(savedBook);
        assertNotNull(savedBook.getId());
        assertEquals("The Adventures of Sherlock Holmes", savedBook.getTitle());
        verify(repository, times(1)).save(book);
    }

    @Test
    public void shouldNotAddBookWithoutData() {
        Book book = new Book();

        // Assert
        assertThrows(MissingFieldsException.class, () -> service.addBook(book));
    }

    @Test
    public void shouldNotAddBookWithoutGoodISBN() {
        Book book = new Book("978-1941797544", "The Adventures of Sherlock Holmes", "Arthur Conan Doyle", "Nielsen UK", Format.ROMAN, true);

        // Assert
        assertThrows(BadArgumentException.class, () -> service.addBook(book));
    }
}
