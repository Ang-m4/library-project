package com.project.library.controller;

import com.project.library.Db.BookRepository;
import com.project.library.Db.OrderRepository;
import com.project.library.Db.SubscriptionRepository;
import com.project.library.model.Book;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @GetMapping("/list")
    public List<Book> getList() {

        List<Book> list = (List<Book>) bookRepository.findAll();

        if(list.size() == 0){
            logger.error("No books found in the database");
        }
        else{
            logger.info("Getting books from database");
        }
        return list;
    }

    @GetMapping("/{isbn}")
    public Book getBook(@PathVariable Long isbn) {

        Book book = null;

        if(bookRepository.findById(isbn).isPresent()){
            logger.info("Getting book with isbn {}", isbn);
            book = bookRepository.findById(isbn).get();
        }
        else{
            logger.error("Book with isbn {} not found", isbn);
        }
        return  book;
    }
    
    @PostMapping("/add")
    public Book addBook(@RequestBody Book book) {

        Book newBook = bookRepository.save(book);
        logger.info("Saving book {} to the database", newBook.getIsbn());
        
        return newBook;
    }

    @PutMapping("/{isbn}/update")
    public Book updateBook(@PathVariable Long isbn, @RequestBody Book book) {
        
        book.setIsbn(isbn);
        Book updatedBook = bookRepository.save(book);
        logger.info("Updating book {} , and sending to the database", updatedBook.getIsbn());

        return updatedBook;
    }

    @DeleteMapping("/{isbn}/delete")
    public void deleteBook(@PathVariable Long isbn) {

        if(bookRepository.findById(isbn).isPresent()){
            
            if( orderRepository.findByBookIsbn(isbn).size() > 0 || 
                subscriptionRepository.findByBookIsbn(isbn).size() > 0){

                    logger.error("Book with isbn {} is in use, cannot delete", isbn);
            }
            else{
                bookRepository.deleteById(isbn);
                logger.info("Deleting book with isbn {}", isbn);
            }
        }
        else{
            logger.error("Book with isbn {} not found", isbn);
        }

    }

}
