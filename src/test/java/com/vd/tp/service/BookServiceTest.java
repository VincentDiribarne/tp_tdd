package com.vd.tp.service;

import com.vd.tp.exception.service.AlreadyExistsException;
import com.vd.tp.exception.service.BadArgumentException;
import com.vd.tp.exception.service.MissingFieldsException;
import com.vd.tp.exception.service.NotFoundException;
import com.vd.tp.exchange.BookApiClient;
import com.vd.tp.model.ApiResponse;
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

import java.util.List;
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

    @Mock
    private BookApiClient client;

    @InjectMocks
    private BookService service;

    @BeforeEach
    void setUp() {
        repository = mock(BookRepository.class);
        client = mock(BookApiClient.class);
        service = new BookService(repository, client);
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

    /* Title */
    @Test
    public void shouldFindOneBookByLikeTitle() {
        //Given
        Book book = new Book("978-1917067287", "The Adventures of Sherlock Holmes", "Arthur Conan Doyle", "Nielsen UK", Format.ROMAN, true);

        //Should
        when(repository.findBooksByTitleContaining(anyString())).thenReturn(List.of(book));

        List<Book> foundBooks = service.findBooksByTitleContaining("Sherlock");

        // Assert
        assertNotNull(foundBooks.getFirst());
        assertEquals("The Adventures of Sherlock Holmes", foundBooks.getFirst().getTitle());

        verify(repository, times(1)).findBooksByTitleContaining("Sherlock");
    }

    @Test
    public void shouldFindBooksByLikeTitle() {
        //Given
        Book book = new Book("978-1917067287", "The Adventures of Sherlock Holmes", "Arthur Conan Doyle", "Nielsen UK", Format.ROMAN, true);
        Book secondBook = new Book("979-8301506079", "Sherlock Holmes et les vacances précipitées", "Mabel Swift", "Independently published", Format.ROMAN, true);

        //Should
        when(repository.findBooksByTitleContaining(anyString())).thenReturn(List.of(book, secondBook));

        List<Book> foundBooks = service.findBooksByTitleContaining("Sherlock");

        // Assert
        assertNotNull(foundBooks.getFirst());
        assertEquals("The Adventures of Sherlock Holmes", foundBooks.getFirst().getTitle());

        assertNotNull(foundBooks.get(1));
        assertEquals("Sherlock Holmes et les vacances précipitées", foundBooks.get(1).getTitle());

        verify(repository, times(1)).findBooksByTitleContaining("Sherlock");
    }

    @Test
    public void shouldNotFindBooksByLikeTitle() {
        //Given

        //Should
        when(repository.findBooksByTitleContaining(anyString())).thenReturn(List.of());

        List<Book> foundBooks = service.findBooksByTitleContaining("Sherlock");

        // Assert
        assertTrue(foundBooks.isEmpty());

        verify(repository, times(1)).findBooksByTitleContaining("Sherlock");
    }

    /* Author */
    @Test
    public void shouldFindOneBookByAuthor() {
        //Given
        Book book = new Book("978-1917067287", "The Adventures of Sherlock Holmes", "Arthur Conan Doyle", "Nielsen UK", Format.ROMAN, true);

        //Should
        when(repository.findBooksByAuthorContaining(anyString())).thenReturn(List.of(book));

        List<Book> foundBooks = service.findBooksByAuthorContaining("Arthur Conan Doyle");

        // Assert

        assertNotNull(foundBooks.getFirst());
        assertEquals("The Adventures of Sherlock Holmes", foundBooks.getFirst().getTitle());

        verify(repository, times(1)).findBooksByAuthorContaining("Arthur Conan Doyle");
    }

    @Test
    public void shouldFindBooksByAuthor() {
        //Given
        Book book = new Book("978-1917067287", "The Adventures of Sherlock Holmes", "Arthur Conan Doyle", "Nielsen UK", Format.ROMAN, true);
        Book secondBook = new Book("979-8301506079", "Sherlock Holmes et les vacances précipitées", "Arthur Conan Doyle", "Independently published", Format.ROMAN, true);

        //Should
        when(repository.findBooksByAuthorContaining(anyString())).thenReturn(List.of(book, secondBook));

        List<Book> foundBooks = service.findBooksByAuthorContaining("Arthur Conan Doyle");

        // Assert
        assertNotNull(foundBooks.getFirst());
        assertEquals("The Adventures of Sherlock Holmes", foundBooks.getFirst().getTitle());

        assertNotNull(foundBooks.get(1));
        assertEquals("Sherlock Holmes et les vacances précipitées", foundBooks.get(1).getTitle());

        verify(repository, times(1)).findBooksByAuthorContaining("Arthur Conan Doyle");
    }

    @Test
    public void shouldNotFindBooksByAuthor() {
        // Given
        when(repository.findBooksByAuthorContaining("Anne Robillard")).thenReturn(List.of());

        // When
        List<Book> foundBooks = service.findBooksByAuthorContaining("Anne Robillard");

        // Then
        assertTrue(foundBooks.isEmpty());

        verify(repository, times(1)).findBooksByAuthorContaining("Anne Robillard");
    }


    /* Save */
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


    /* Add */
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
    public void shouldNotAddBookWithoutISBN() {
        Book book = new Book();

        // Assert
        assertThrows(MissingFieldsException.class, () -> service.addBook(book));
        verify(repository, never()).save(book);
    }

    @Test
    public void shouldNotAddBookWithoutBadISBN() {
        Book book = new Book("978-1941797544", "The Adventures of Sherlock Holmes", "Arthur Conan Doyle", "Nielsen UK", Format.ROMAN, true);

        // Assert
        assertThrows(BadArgumentException.class, () -> service.addBook(book));
        verify(repository, never()).save(book);
    }

    @Test
    public void shouldAddBookWithAPI() {
        //Given
        Book book = new Book("978-1917067287", null, null, null, null, true);
        ApiResponse response = new ApiResponse("The Adventures of Sherlock Holmes", "Arthur Conan Doyle", "Nielsen UK", "ROMAN");

        when(client.callAPI(any(Book.class))).thenReturn(response);

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
        assertEquals("Arthur Conan Doyle", savedBook.getAuthor());
        assertEquals("Nielsen UK", savedBook.getEditor());
        assertEquals(Format.ROMAN, savedBook.getFormat());

        verify(client, times(1)).callAPI(book);
        verify(repository, times(1)).save(book);
    }

    @Test
    public void shouldNotAddBookWithApiIsbnNotExist() {
        //Given
        Book book = new Book("978-1917067287", null, null, null, null, true);

        when(client.callAPI(book)).thenThrow(new NotFoundException("Book not found"));

        // Assert
        assertThrows(NotFoundException.class, () -> service.addBook(book));

        verify(client, times(1)).callAPI(book);
        verify(repository, never()).save(book);
    }

    @Test
    public void shouldNotAddBookAlreadyExist() {
        //Given
        Book book = new Book("978-1917067287", "The Adventures of Sherlock Holmes", "Arthur Conan Doyle", "Nielsen UK", Format.ROMAN, true);

        // Spy sur le service pour simuler la méthode bookAlreadyExists
        BookService spyService = spy(service);

        doReturn(true).when(spyService).bookAlreadyExists(any(Book.class));

        assertThrows(AlreadyExistsException.class, () -> spyService.addBook(book));

        verify(repository, never()).save(any(Book.class));
    }


    /* Update */
    @Test
    public void shouldUpdateBookSuccessfully() {
        //Given
        Book book = new Book("978-1917067287", "The Adventures of Sherlock Holmes", "Arthur Conan Doyle", "Nielsen UK", Format.ROMAN, true);

        //Should
        when(repository.existsById(anyString())).thenReturn(true);

        when(repository.save(any(Book.class))).thenAnswer(invocation -> {
            Book savedBook = invocation.getArgument(0);
            savedBook.setId(UUID.randomUUID().toString());
            return savedBook;
        });

        Book updatedBook = service.updateBook(UUID.randomUUID().toString(), book);

        // Assert
        assertNotNull(updatedBook);
        assertNotNull(updatedBook.getId());
        assertEquals("The Adventures of Sherlock Holmes", updatedBook.getTitle());
        verify(repository, times(1)).existsById(anyString());
        verify(repository, times(1)).save(book);
    }

    @Test
    public void shouldNotUpdateBook() {
        //Given
        Book book = new Book("978-1917067287", "The Adventures of Sherlock Holmes", "Arthur Conan Doyle", "Nielsen UK", Format.ROMAN, true);

        //Should
        when(repository.existsById(anyString())).thenReturn(false);

        // Assert
        assertThrows(NotFoundException.class, () -> service.updateBook(UUID.randomUUID().toString(), book));
        verify(repository, times(1)).existsById(anyString());
        verify(repository, never()).save(book);
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