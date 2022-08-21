package com.project.library.Db;

import com.project.library.model.Book;
import com.project.library.model.User;

import java.time.LocalDate;

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

    }
}
