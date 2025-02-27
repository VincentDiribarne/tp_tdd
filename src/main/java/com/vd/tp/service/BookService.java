package com.vd.tp.service;

import com.vd.tp.exception.validator.MissingFieldsException;
import com.vd.tp.model.Book;
import com.vd.tp.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository repository;

    public Book addBook(Book book) {
        missingFields(book);

        return repository.save(book);
    }

    public Book saveBook(Book book) {
        return repository.save(book);
    }

    private void missingFields(Book book) {
        if (book.getIsbn() == null) throw new MissingFieldsException("ISBN is required");

        if (book.getTitle() == null) throw new MissingFieldsException("Title is required");

        if (book.getAuthor() == null) throw new MissingFieldsException("Author is required");

        if (book.getEditor() == null) throw new MissingFieldsException("Editor is required");

        if (book.getFormat() == null) throw new MissingFieldsException("Format is required");
    }
}
