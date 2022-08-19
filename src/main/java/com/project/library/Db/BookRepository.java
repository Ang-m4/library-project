package com.project.library.Db;

import org.springframework.data.repository.CrudRepository;
import com.project.library.model.Book;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

}
