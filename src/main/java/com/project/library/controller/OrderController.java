package com.project.library.controller;

import com.project.library.Db.BookRepository;
import com.project.library.Db.OrderRepository;
import com.project.library.Db.ReaderRepository;
import com.project.library.model.Book;
import com.project.library.model.Order;
import com.project.library.model.Reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ReaderRepository readerRepository;

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/list")
    public List<Order> getList() {

        List<Order> list = (List<Order>) orderRepository.findAll();

        if (list.size() == 0) {
            logger.error("No orders found in the database");
        } else {
            logger.info("Getting orders from database");
        }
        return list;
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable long id) {

        Order order = null;

        if (orderRepository.findById(id).isPresent()) {
            logger.info("Getting order with id {}", id);
            order = orderRepository.findById(id).get();
        } else {
            logger.error("Order with id {} not found", id);
        }
        return order;
    }

    @PostMapping("/add")
    public Order addOrder(@RequestParam Long idUser, @RequestParam Long idBook) {

        Order savedOrder = null;

        if (readerRepository.findById(idUser).isPresent() && bookRepository.findById(idBook).isPresent()) {

            Book book = bookRepository.findById(idBook).get();
            Reader reader = readerRepository.findById(idUser).get();

            if (book.getCopies() > 0) {

                savedOrder = orderRepository.save(new Order(-1, reader, book, LocalDate.now()));

                book.setCopies(book.getCopies() - 1);
                bookRepository.save(book);
                logger.info("Adding order with idUser {} and idBook {}", idUser, idBook);

            } else {

                logger.error("Book with id {} has no copies", book.getIsbn());
            }
        } else {
            logger.error("Reader or book not found");
        }
        return savedOrder;
    }

    @DeleteMapping("/{id}/delete")
    public void deleteOrder(@PathVariable Long id) {

        if (orderRepository.findById(id).isPresent()) {

            Book book = orderRepository.findById(id).get().getBook();

            logger.info("Deleting order with id {}", id);
            orderRepository.deleteById(id);

            book.setCopies(book.getCopies() + 1);
            bookRepository.save(book);

        } else {
            logger.error("Order with id {} not found", id);
        }

    }

}
