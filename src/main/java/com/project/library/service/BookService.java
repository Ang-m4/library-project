package com.project.library.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.project.library.error.RequestException;
import com.project.library.model.Book;
import com.project.library.repository.BookRepository;
import com.project.library.repository.OrderRepository;
import com.project.library.repository.SubscriptionRepository;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    SubscriptionRepository subscriptionRepository;

    public List<Book> getAllBooks(String sortField, String direction, int size, String filter) {
        List<Book> nonFilteredBooks = (List<Book>) bookRepository.findAll(sortField, direction, size);
        if (!filter.equals("")) {
            return nonFilteredBooks.stream()
                    .filter(book -> book.getTitle().toLowerCase().contains(filter.toLowerCase())).toList();
        }
        return nonFilteredBooks;
    }

    public Book findBookByIsbn(String isbn) {
        Optional<Book> book = bookRepository.findByISBN(isbn);
        if (!book.isPresent()) {
            throw new RequestException(HttpStatus.NOT_FOUND,"Book with isbn " + isbn + " was not found");
        }
        return book.get();
    }

    public Book saveBook(Book book) {
        if (bookRepository.findByISBN(book.getIsbn()).isPresent()) {
            throw new RequestException(HttpStatus.NOT_FOUND, "Book with isbn "  + book.getIsbn() + " already exist");
        }
        Book savedBook = bookRepository.save(book);
        return savedBook;
    }

    public void deleteBook(String isbn) {
        if (bookRepository.findByISBN(isbn).isPresent()) {
            if (orderRepository.findByBookIsbn(isbn).isEmpty()
                    && subscriptionRepository.findByBookIsbn(isbn).isEmpty()) {
                bookRepository.deleteByISBN(isbn);
            } else {
                throw new RequestException(HttpStatus.NOT_FOUND, "Book with isbn " + isbn + "is in use");
            }
        } else {
            throw new RequestException(HttpStatus.NOT_FOUND, "Book with isbn " + isbn + "does not exist");
        }
    }

    public Book updateBook(Book book) {
        Optional<Book> bookToUpdate = bookRepository.findByISBN(book.getIsbn());
        if (!bookToUpdate.isPresent()) {
            throw new RequestException(HttpStatus.NOT_FOUND, "Book with isbn " + book.getIsbn() + " does not exist, add it as a new book if necesary");
        }
        Book updatedBook = Book.builder().
            id(bookToUpdate.get().getId()).
            isbn(book.getIsbn()).
            title(book.getTitle()).
            author(book.getAuthor()).
            publishDate(book.getPublishDate()).
            copies(book.getCopies()).
            publish(book.getPublish()).
            build();
        return bookRepository.save(updatedBook);
    }

}
