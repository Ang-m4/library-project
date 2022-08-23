package com.project.library.controller;
import com.project.library.Db.BookRepository;
import com.project.library.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/list")
    public List<Book> getList() {
        return (List<Book>)bookRepository.findAll();
    }

    @GetMapping("/{isbn}")
    public Book getBook(@PathVariable Long isbn) {
        return bookRepository.findById(isbn).get();
    }
    
    @PostMapping("/add")
    public Book addBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @PutMapping("/{isbn}/update")
    public Book updateBook(@PathVariable Long isbn, @RequestBody Book book) {
        
        book.setIsbn(isbn);
        return bookRepository.save(book);
    }

    @DeleteMapping("/{isbn}/delete")
    public void deleteBook(@PathVariable Long isbn) {
        bookRepository.deleteById(isbn);

    }

}
