package com.project.library.controller;

import com.project.library.Db.BookRepository;
import com.project.library.Db.OrderRepository;
import com.project.library.Db.SubscriptionRepository;
import com.project.library.model.Book;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Get the catalog of books")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List all the books",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Catalog not found",
                    content = @Content) })
    @GetMapping("/list")
    public List<Book> getList() {

        List<Book> list = (List<Book>) bookRepository.findAll();

        if (list.size() == 0) {
            logger.error("No books found in the database");
        } else {
            logger.info("Getting books from database");
        }
        return list;
    }

    @Operation(summary = "Get the catalog of books order by author(A-Z)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List all the books order by author(A-Z)",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Catalog not found",
                    content = @Content) })
    @GetMapping("/list/author")
    public List<Book> getListByAuthor() {

        List<Book> list = (List<Book>) bookRepository.findOrderedByAuthor();

        if (list.size() == 0) {
            logger.error("No books found in the database");
        } else {
            logger.info("Getting books from database");
        }
        return list;
    }
    
    @Operation(summary = "Get the catalog of books order by name(A-Z)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List all the books order by name(A-Z)",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Catalog not found",
                    content = @Content) })
    @GetMapping("/list/name")
    public List<Book> getListByName() {

        List<Book> list = (List<Book>) bookRepository.findOrderedByName();

        if (list.size() == 0) {
            logger.error("No books found in the database");
        } else {
            logger.info("Getting books from database");
        }
        return list;
    }

    @Operation(summary = "Get the catalog of books order by publish(A-Z)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List all the books order by publish(A-Z)",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Catalog not found",
                    content = @Content) })
    @GetMapping("/list/publish")
    public List<Book> getListByPublish() {

        List<Book> list = (List<Book>) bookRepository.findOrderedByPublish();

        if (list.size() == 0) {
            logger.error("No books found in the database");
        } else {
            logger.info("Getting books from database");
        }
        return list;
    }

    @Operation(summary = "Get the catalog of books order by publication date")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List all the books order by publication date",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Catalog not found",
                    content = @Content) })
    @GetMapping("/list/date")
    public List<Book> getListByDate() {

        List<Book> list = (List<Book>) bookRepository.findOrderedByPublishDate();

        if (list.size() == 0) {
            logger.error("No books found in the database");
        } else {
            logger.info("Getting books from database");
        }
        return list;
    }

    @Operation(summary = "Get the book by its isbn")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the book",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Book not found",
                    content = @Content) })
    @GetMapping("/{isbn}")
    public Book getBook(@Parameter(description = "id of book to be searched") @PathVariable Long isbn) {

        Book book = null;

        if (bookRepository.findById(isbn).isPresent()) {
            logger.info("Getting book with isbn {}", isbn);
            book = bookRepository.findById(isbn).get();
        } else {
            logger.error("Book with isbn {} not found", isbn);
        }
        return book;
    }

    @Operation(summary = "Add a book to the catalog")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book added",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Book not added",
                    content = @Content) })
    @PostMapping("/add")
    public Book addBook(@RequestBody Book book) {

        Book newBook = bookRepository.save(book);
        logger.info("Saving book {} to the database", newBook.getIsbn());

        return newBook;
    }

    @Operation(summary = "Update a book in the catalog by its isbn")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Book not found",
                    content = @Content) })
    @PutMapping("/{isbn}/update")
    public Book updateBook(@Parameter(description = "isbn of book to be updated") @PathVariable Long isbn, @RequestBody Book book) {

        book.setIsbn(isbn);
        Book updatedBook = bookRepository.save(book);
        logger.info("Updating book {} , and sending to the database", updatedBook.getIsbn());

        return updatedBook;
    }

    @Operation(summary = "Delete a book in the catalog by its isbn")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Book not found",
                    content = @Content) })
    @DeleteMapping("/{isbn}/delete")
    public void deleteBook(@Parameter(description = "isbn of book to be deleted") @PathVariable Long isbn) {

        if (bookRepository.findById(isbn).isPresent()) {

            if (orderRepository.findByBookIsbn(isbn).size() > 0 ||
                    subscriptionRepository.findByBookIsbn(isbn).size() > 0) {

                logger.error("Book with isbn {} is in use, cannot delete", isbn);
            } else {
                bookRepository.deleteById(isbn);
                logger.info("Deleting book with isbn {}", isbn);
            }
        } else {
            logger.error("Book with isbn {} not found", isbn);
        }

    }

}
