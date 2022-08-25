package com.project.library.Db;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.project.library.model.Book;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

    @Query("SELECT b FROM Book b ORDER BY b.author")
    List<Book> findOrderedByAuthor();

    @Query("SELECT b FROM Book b ORDER BY b.name")
    List<Book> findOrderedByName();

    @Query("SELECT b FROM Book b ORDER BY b.publish")
    List<Book> findOrderedByPublish();

    @Query("SELECT b FROM Book b ORDER BY b.publishDate")
    List<Book> findOrderedByPublishDate();

}
