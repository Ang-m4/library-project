package com.project.library.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;

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
import com.project.library.Db.SubscriptionRepository;
import com.project.library.model.Book;
import com.project.library.model.Order;
import com.project.library.model.User;
import com.project.library.model.Subscription;

@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SubscriptionControllerIntegrationTest {

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

    @Autowired
    private SubscriptionRepository subscriptionRepository;

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
    
    @Test
    void testAddSubscription() {

        int duration = 15;

        Order order = rest.postForObject("http://localhost:" + port + "/order/add?idUser=1&idBook=1", null,
                Order.class);
        assertEquals(1, orderRepository.count());

        Subscription subscription = rest.postForEntity(
                "http://localhost:" + port + "/subscription/add?idOrder=" + order.getId() + "&duration=" + duration,
                null, Subscription.class).getBody();

        assertEquals(0, orderRepository.count());
        assertEquals(1, subscriptionRepository.count());
        assertEquals(order.getBook().getName(), subscription.getBook().getName());
        assertEquals(order.getUser().getName(), subscription.getUser().getName());
        assertEquals(subscription.getGivenDate().plusDays(duration), subscription.getReturnDate());

    }

    @Test
    void testDeleteSubscription() {
        int duration = 15;
        Order order = rest.postForObject("http://localhost:" + port + "/order/add?idUser=1&idBook=1", null,
                Order.class);
        Subscription subscription = rest.postForEntity(
                "http://localhost:" + port + "/subscription/add?idOrder=" + order.getId() + "&duration=" + duration,
                null, Subscription.class).getBody();

        assertEquals(2, subscription.getBook().getCopies());

        rest.delete("http://localhost:" + port + "/subscription/" + subscription.getId() + "/delete");

        assertEquals(0, subscriptionRepository.count());
        assertEquals(3,bookRepository.findById(subscription.getBook().getIsbn()).get().getCopies());

    }

    @Test
    void testGetList() {

        int duration = 15;

        Order order = rest.postForObject("http://localhost:" + port + "/order/add?idUser=1&idBook=1", null,
                Order.class);

        Order order2 = rest.postForObject("http://localhost:" + port + "/order/add?idUser=1&idBook=2", null,
                Order.class);

        rest.postForEntity(
                "http://localhost:" + port + "/subscription/add?idOrder=" + order.getId() + "&duration=" + duration,
                null, Subscription.class);

        rest.postForEntity(
                "http://localhost:" + port + "/subscription/add?idOrder=" + order2.getId() + "&duration=" + duration,
                null, Subscription.class);

        assertEquals(2, subscriptionRepository.count());

    }

    @Test
    void testGetSubscription() {

        int duration = 15;
        Order order = rest.postForObject("http://localhost:" + port + "/order/add?idUser=1&idBook=1", null,
                Order.class);

        rest.postForEntity("http://localhost:" + port + "/subscription/add?idOrder=" + order.getId() + "&duration=" + duration,
                null, Subscription.class);

        Subscription subscription = rest.getForObject("http://localhost:" + port + "/subscription/4", Subscription.class);
        assertEquals(1, subscriptionRepository.count());

        assertEquals("Harry Potter", subscription.getBook().getName());
        assertEquals("John reader", subscription.getUser().getName());

    }

    @Test
    void testUpdateSubscription() {

        int duration = 15;
        int plus = 10;
        Order order = rest.postForObject("http://localhost:" + port + "/order/add?idUser=1&idBook=1", null,
                Order.class);

        rest.postForEntity("http://localhost:" + port + "/subscription/add?idOrder=" + order.getId() + "&duration=" + duration,
                null, Subscription.class);
                
        Subscription subscription = rest.getForObject("http://localhost:" + port + "/subscription/4", Subscription.class);
        
        rest.put("http://localhost:" + port + "/subscription/4/update?plus=" + plus,null);

        Subscription subscriptionUpdated = rest.getForObject("http://localhost:" + port + "/subscription/4", Subscription.class);

        assertEquals(subscription.getReturnDate().plusDays(plus), subscriptionUpdated.getReturnDate());

    }
}
