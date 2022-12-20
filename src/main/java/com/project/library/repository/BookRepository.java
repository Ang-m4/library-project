package com.project.library.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.project.library.model.Book;
import org.springframework.transaction.annotation.Transactional;

public interface BookRepository extends CrudRepository<Book, Long> {

    @Query(value = "SELECT * FROM books b ORDER BY ?1 ?2 LIMIT ?3", nativeQuery = true)
    List<Book> findAll(String sortField, String direction ,Integer size);

    @Query(value = "SELECT * FROM books b WHERE b.book_isbn = ?1", nativeQuery = true)
    Optional<Book> findByISBN(String isbn);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM books b WHERE b.book_isbn = ?1", nativeQuery = true)
    Integer deleteByISBN(String isbn);
}
