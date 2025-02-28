package com.vd.tp.repository;

import com.vd.tp.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
    List<Book> findBooksByTitleContaining(String title);

    List<Book> findBooksByAuthorContaining(String author);

    Optional<Book> findByIsbn(String isbn);
}