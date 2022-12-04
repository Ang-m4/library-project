package com.project.library.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import com.project.library.model.Book;
import com.project.library.repository.BookRepository;

@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BookControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TestRestTemplate rest;

    @BeforeEach
    public void init() {
        
        Book book = new Book(1, "Harry Potter", "AJK Rowling", LocalDate.of(1997, 12, 31), 1, "Vol. 1");
        Book book2 = new Book(2, "The Lord of the Rings", "BJRR Tolkien", LocalDate.of(1954, 12, 31), 1, "Vol. 2");
        Book book3 = new Book(3, "The Hobbit", "DJRR Tolkien", LocalDate.of(1937, 12, 31), 1, "Vol. 4");
        Book book4 = new Book(4, "The Catcher in the Rye", "EJK Rowling", LocalDate.of(1951, 12, 31), 1, "Vol. 3");

        bookRepository.saveAll(List.of(book, book2, book3, book4));

    }

    @AfterEach
    public void clean(){
        bookRepository.deleteAll();
    }

    @Test
    void testAddBook() {

        Book book = new Book(5l, "The Giver", "CLois Lowry", LocalDate.of(1936, 12, 31), 1, "Vol. 1");
        rest.postForEntity("http://localhost:" + port + "/book/add", book, Book.class);
        
        assertEquals(true, bookRepository.findById(5l).isPresent());

    }

    @Test
    void testDeleteBook() {

        Book book = new Book(5l, "The Giver", "CLois Lowry", LocalDate.of(1936, 12, 31), 1, "Vol. 1");
        rest.postForEntity("http://localhost:" + port + "/book/add", book, Book.class);
        assertEquals(true, bookRepository.findById(5l).isPresent());
        rest.delete("http://localhost:" + port + "/book/5/delete");
        assertEquals(false, bookRepository.findById(5l).isPresent());
    }

    @Test
    void testGetBook() {

        Book book = rest.getForEntity("http://localhost:" + port + "/book/1", Book.class).getBody();
        assertEquals("Harry Potter", book.getName());

        Book book2 = rest.getForEntity("http://localhost:" + port + "/book/2", Book.class).getBody();
        assertEquals("The Lord of the Rings", book2.getName());

    }

    @Test
    void testGetList() {
        
        Book[] books = rest.getForObject("http://localhost:" + port + "/book/list", Book[].class);
        assertEquals(4, books.length);

        Book book = new Book(5l, "The Giver", "CLois Lowry", LocalDate.of(1936, 12, 31), 1, "Vol. 1");
        rest.postForEntity("http://localhost:" + port + "/book/add", book, Book.class);

        Book[] booksPlus = rest.getForObject("http://localhost:" + port + "/book/list", Book[].class);
        assertEquals(5, booksPlus.length);

    }

    @Test
    void testGetListByAuthor() {

        Book[] books = rest.getForObject("http://localhost:" + port + "/book/list/author", Book[].class);
        assertEquals(4, books.length);
        
        assertEquals("AJK Rowling",books[0].getAuthor());
        assertEquals("BJRR Tolkien",books[1].getAuthor());
        assertEquals("DJRR Tolkien",books[2].getAuthor());
        assertEquals("EJK Rowling",books[3].getAuthor());

    }

    @Test
    void testGetListByDate() {
            
        Book[] books = rest.getForObject("http://localhost:" + port + "/book/list/date", Book[].class);
        assertEquals(4, books.length);
            
        assertEquals(LocalDate.of(1937, 12, 31), books[0].getpublishDate());
        assertEquals(LocalDate.of(1951, 12, 31), books[1].getpublishDate());
        assertEquals(LocalDate.of(1954, 12, 31), books[2].getpublishDate());
        assertEquals(LocalDate.of(1997, 12, 31), books[3].getpublishDate());
            
    }

    @Test
    void testGetListByName() {

        Book[] books = rest.getForObject("http://localhost:" + port + "/book/list/name", Book[].class);
        assertEquals(4, books.length);
        
        assertEquals("Harry Potter",books[0].getName());
        assertEquals("The Catcher in the Rye",books[1].getName());
        assertEquals("The Hobbit",books[2].getName());
        assertEquals("The Lord of the Rings",books[3].getName());
        
    }

    @Test
    void testGetListByPublish() {

        Book[] books = rest.getForObject("http://localhost:" + port + "/book/list/publish", Book[].class);
        assertEquals(4, books.length);
        
        assertEquals("Vol. 1",books[0].getPublish());
        assertEquals("Vol. 2",books[1].getPublish());
        assertEquals("Vol. 3",books[2].getPublish());
        assertEquals("Vol. 4",books[3].getPublish());

    }

    @Test
    void testUpdateBook() {

        Book book = new Book(5l, "The Giver", "CLois Lowry", LocalDate.of(1936, 12, 31), 1, "Vol. 1");
        rest.postForEntity("http://localhost:" + port + "/book/add", book, Book.class);
        assertEquals(true, bookRepository.findById(5l).isPresent());
        book.setName("The Giver 2");
        rest.put("http://localhost:" + port + "/book/5/update", book);
        assertEquals("The Giver 2", bookRepository.findById(5l).get().getName());

    }
}
