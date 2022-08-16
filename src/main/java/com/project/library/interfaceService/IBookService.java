package com.project.library.interfaceService;

import com.project.library.model.Book;
import java.util.List;
import java.util.Optional;

public interface IBookService {
    public List<Book>listBook();
    public Optional<Book> searchIdBook(long id);
    public int saveBook(Book b);
    public void deleteBook(long id);
}
