/*
    @author Andres Malagon
    @GitHub Ang-m4
*/

package com.project.library.controller;

import com.project.library.model.Book;
import com.project.library.service.BookService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/books")
public class BookController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private BookService bookService;

    @Operation(summary = "Get the books catalog")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Returns a list of books ordered and filtered based on query parameters", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) }),
        @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
        @ApiResponse(responseCode = "404", description = "Catalog not found", content = @Content)})
    @GetMapping("/list")
    public ResponseEntity<List<Book>>getList(
        @RequestParam(value = "sortField", required = false, defaultValue = "book_isbn") String sortField,
        @RequestParam(value = "direction", required = false, defaultValue = "ASC") String direction,
        @RequestParam(value = "size", required = false, defaultValue = "100") int size,
        @RequestParam(value = "filter", required = false, defaultValue = "") String filter
        ) {
        List<Book> list = (List<Book>) bookService.getAllBooks(sortField,direction,size,filter);
        return ResponseEntity.ok().body(list);
    }

    @Operation(summary = "Get a book by its ISBN")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the book",content = { @Content(mediaType = "application/json",schema = @Schema(implementation = Book.class)) }),
        @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
        @ApiResponse(responseCode = "404", description = "Book not found", content = @Content) })
    @GetMapping("/{isbn}")
    public ResponseEntity<Book> getBook(@Parameter(description = "id of book to be searched") @PathVariable String isbn) {
        Book book = bookService.findBookByIsbn(isbn);
        return ResponseEntity.ok().body(book);
    }

    @Operation(summary = "Add a book to the catalog")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Book added", content = { @Content(mediaType = "application/json",schema = @Schema(implementation = Book.class)) }),
        @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
        @ApiResponse(responseCode = "404", description = "Book not added", content = @Content) })
    @PostMapping("/add")
    public ResponseEntity<Book> addBook(@RequestBody @Valid Book book) {
        Book newBook = bookService.saveBook(book);
        logger.info("Saving book {} to the database", newBook.getIsbn());
        return ResponseEntity.status(HttpStatus.CREATED).body(newBook);
    }

    @Operation(summary = "Update a book in the catalog")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Book updated", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) }),
        @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
        @ApiResponse(responseCode = "404", description = "Book not found", content = @Content) })
    @PutMapping("/update")
    public ResponseEntity<Book> updateBook(@RequestBody @Valid Book book) {
        Book updatedBook = bookService.updateBook(book);
        logger.info("Updating book {} , and sending to the database", updatedBook.getIsbn());
        return ResponseEntity.ok().body(updatedBook);
    }

    @Operation(summary = "Delete a book in the catalog by its isbn")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Book deleted", content = @Content),
        @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
        @ApiResponse(responseCode = "404", description = "Book not found", content = @Content) })
    @DeleteMapping("/{isbn}/delete")
    public ResponseEntity<String> deleteBook(@Parameter(description = "isbn of book to delete") @PathVariable String isbn) {
        bookService.deleteBook(isbn);
        logger.info("Deleting book with isbn {}", isbn);
        return ResponseEntity.ok().body("Book with isbn " + isbn + " deleted");
    }

}
