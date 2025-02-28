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
import com.vd.tp.validator.ISBNValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository repository;
    private final BookApiClient client;

    public Book findBookByISBN(String isbn) {
        return repository.findByIsbn(isbn).orElseThrow(() -> new NotFoundException("Book with isbn '" + isbn + "' not found"));
    }

    public List<Book> findBooksByTitleContaining(String title) {
        return repository.findBooksByTitleContaining(title);
    }

    public List<Book> findBooksByAuthorContaining(String author) {
        return repository.findBooksByAuthorContaining(author);
    }

    public boolean bookAlreadyExists(Book book) {
        Book returnBook = repository.findAll().stream().filter(b -> b.getIsbn().equals(book.getIsbn())
                && b.getTitle().equals(book.getTitle())
                && b.getAuthor().equals(book.getAuthor())
                && b.getEditor().equals(book.getEditor())
                && b.getFormat().equals(book.getFormat())
        ).findFirst().orElse(null);

        return returnBook != null;
    }

    public Book addBook(Book book) {
        if (book.getIsbn() == null) throw new MissingFieldsException("ISBN is required");

        ISBNValidator isbnValidator = new ISBNValidator();
        if (!isbnValidator.validate(book.getIsbn())) throw new BadArgumentException("Invalid ISBN");

        if (book.getTitle() == null || book.getAuthor() == null || book.getEditor() == null || book.getFormat() == null) {
            ApiResponse response = client.callAPI(book);
            if (response == null) throw new NotFoundException("Book with isbn " + book.getIsbn() + " not found");

            if (book.getTitle() == null) book.setTitle(response.getTitle());
            if (book.getAuthor() == null) book.setAuthor(response.getAuthor());
            if (book.getEditor() == null) book.setEditor(response.getEditor());
            if (book.getFormat() == null) book.setFormat(Format.valueOf(response.getFormat()));
        }

        if (bookAlreadyExists(book)) throw new AlreadyExistsException("Book already exists");

        return repository.save(book);
    }

    public Book updateBook(String id, Book book) {
        if (!repository.existsById(id)) throw new NotFoundException("Book with id " + id + " not found");

        return addBook(book);
    }


    public Book saveBook(Book book) {
        return repository.save(book);
    }


    /* Delete */
    public void deleteById(String id) {
        if (!repository.existsById(id)) throw new NotFoundException("Book with id " + id + " not found");

        repository.deleteById(id);
    }
}
