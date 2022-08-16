package com.project.library.interfaces;

import org.springframework.data.repository.CrudRepository;
import com.project.library.model.Book;

public interface IBook extends CrudRepository<Book, Long> {

}
