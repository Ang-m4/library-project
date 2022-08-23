package com.project.library.Db;

import com.project.library.model.Book;
import com.project.library.model.Order;
import com.project.library.model.Reader;
import com.project.library.model.Subscription;
import com.project.library.model.User;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInit implements ApplicationRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ReaderRepository readerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        User user = new User(1, "John", "Doe", "1234321");
        User user2 = new User(2, "Jane", "Doe", "12321");

        userRepository.save(user);
        userRepository.save(user2);

        Book book = new Book(7, "Harry Potter", "JK Rowling", LocalDate.of(1997, 12, 31), 1, "Bloomsbury");
        Book book2 = new Book(8, "The Lord of the Rings", "JRR Tolkien", LocalDate.of(1954, 12, 31), 1, "Bloomsbury");
        Book book3 = new Book(9, "The Hobbit", "JRR Tolkien", LocalDate.of(1937, 12, 31), 1, "Bloomsbury");
        Book book4 = new Book(10, "The Catcher in the Rye", "JK Rowling", LocalDate.of(1951, 12, 31), 1, "Bloomsbury");
        Book book5 = new Book(11, "The Giver", "Lois Lowry", LocalDate.of(1936, 12, 31), 1, "Bloomsbury");

        bookRepository.save(book);
        bookRepository.save(book2);
        bookRepository.save(book3);
        bookRepository.save(book4);
        bookRepository.save(book5);

        Reader reader = new Reader(3, "John reader", "Doe", "1234321", false, new ArrayList<>(), new ArrayList<>());
        Reader reader2 = new Reader(4, "Jane reader", "Doe", "12321", false, new ArrayList<>(), new ArrayList<>());

        readerRepository.save(reader);
        readerRepository.save(reader2);

        Order order = new Order(22, reader, book, LocalDate.of(2019, 12, 31));
        Order order2 = new Order(23, reader, book2, LocalDate.of(2019, 12, 31));
        Order order3 = new Order(24, reader, book3, LocalDate.of(2019, 12, 31));

        orderRepository.save(order);
        orderRepository.save(order2);
        orderRepository.save(order3);

        Subscription subscription = new Subscription(25, reader, book4, LocalDate.of(2019, 12, 31), LocalDate.of(2019, 12, 31), 0);
        Subscription subscription2 = new Subscription(26, reader, book5, LocalDate.of(2019, 12, 31), LocalDate.of(2019, 12, 31), 0);

        subscriptionRepository.save(subscription);
        subscriptionRepository.save(subscription2);

    }
}
