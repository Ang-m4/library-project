package com.project.library.service;
import com.project.library.interfaceService.IBookService;
import com.project.library.interfaces.IBook;
import com.project.library.model.Book;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;


public class BookService implements IBookService {
    @Autowired
    private IBook dataBook;
    @Override
    public List<Book> listBook() {
        return (List<Book>)dataBook.findAll();
    }

    @Override
    public Optional<Book> searchIdBook(long id) {
        return null;
    }

    @Override
    public int saveBook(Book b) {
        return 0;
    }

    @Override
    public void deleteBook(long id) {
    }
}

