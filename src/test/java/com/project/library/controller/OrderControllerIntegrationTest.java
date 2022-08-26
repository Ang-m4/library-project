package com.project.library.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.ArrayList;

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

import com.project.library.Db.BookRepository;
import com.project.library.Db.OrderRepository;
import com.project.library.Db.UserRepository;
import com.project.library.model.Book;
import com.project.library.model.Order;
import com.project.library.model.User;

@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class OrderControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestRestTemplate rest;

    @BeforeEach
    public void init() {

        Book book = new Book(1, "Harry Potter", "AJK Rowling", LocalDate.of(1997, 12, 31), 3, "Vol. 1");
        Book book2 = new Book(2, "The Lord of the Rings", "BJRR Tolkien", LocalDate.of(1954, 12, 31), 3, "Vol. 2");
        Book book3 = new Book(3, "The Hobbit", "DJRR Tolkien", LocalDate.of(1937, 12, 31), 3, "Vol. 4");
        Book book4 = new Book(4, "The Catcher in the Rye", "EJK Rowling", LocalDate.of(1951, 12, 31), 3, "Vol. 3");

        bookRepository.save(book);
        bookRepository.save(book2);
        bookRepository.save(book3);
        bookRepository.save(book4);

        User reader = new User(1, "John reader", "Doe", "1234321", false, new ArrayList<>(), new ArrayList<>(),"Jhonie","ROLE_READER");
        User reader2 = new User(2, "Jane reader", "Doe", "12321", false, new ArrayList<>(), new ArrayList<>(),"Jenny","ROLE_READER");

        userRepository.save(reader);
        userRepository.save(reader2);
    }

    @AfterEach
    public void clean(){

        orderRepository.deleteAll();
        bookRepository.deleteAll();
        userRepository.deleteAll();

    }

    @Test
    void testAddOrder() {   

        Order order = rest.postForObject("http://localhost:" + port + "/order/add?idUser=1&idBook=1",null, Order.class);
        assertEquals(1, orderRepository.count());
        assertEquals("Harry Potter", order.getBook().getName());
        assertEquals(2, order.getBook().getCopies());
        assertEquals("John reader", order.getUser().getName());

    }

    @Test
    void testDeleteOrder() {

        rest.postForEntity("http://localhost:" + port + "/order/add?idUser=1&idBook=1",null, Order.class);
        rest.postForEntity("http://localhost:" + port + "/order/add?idUser=2&idBook=1",null, Order.class);
        rest.postForEntity("http://localhost:" + port + "/order/add?idUser=1&idBook=2",null, Order.class);

        assertEquals(3, orderRepository.count());
        
        rest.delete("http://localhost:" + port + "/order/3/delete");
        rest.delete("http://localhost:" + port + "/order/4/delete");

        assertEquals(1, orderRepository.count());
        assertEquals(3, bookRepository.findById(1l).get().getCopies());

    }

    @Test
    void testGetList() {

        rest.postForEntity("http://localhost:" + port + "/order/add?idUser=1&idBook=1",null, Order.class);
        rest.postForEntity("http://localhost:" + port + "/order/add?idUser=2&idBook=1",null, Order.class);
        rest.postForEntity("http://localhost:" + port + "/order/add?idUser=1&idBook=2",null, Order.class);
        rest.postForEntity("http://localhost:" + port + "/order/add?idUser=2&idBook=2",null, Order.class);

        assertEquals(4, orderRepository.count());
    }

    @Test
    void testGetOrder() {

        rest.postForObject("http://localhost:" + port + "/order/add?idUser=1&idBook=1",null, Order.class);
        Order getOrder = rest.getForObject("http://localhost:" + port + "/order/3",Order.class);
        assertNotNull(getOrder);

    }
}
