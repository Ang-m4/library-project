package com.project.library.controller;

import com.project.library.model.Book;
import com.project.library.model.Order;
import com.project.library.model.User;
import com.project.library.repository.BookRepository;
import com.project.library.repository.OrderRepository;
import com.project.library.repository.UserRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    private UserRepository readerRepository;

    @Autowired
    private BookRepository bookRepository;

    @Operation(summary = "Get the list of orders")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orders listed",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Order.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Orders not found",
                    content = @Content) })
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

    @Operation(summary = "Get an order by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Order.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Order not found",
                    content = @Content) })
    @GetMapping("/{id}")
    public Order getOrder(@Parameter(description = "id of order to be searched") @PathVariable long id) {

        Order order = null;

        if (orderRepository.findById(id).isPresent()) {
            logger.info("Getting order with id {}", id);
            order = orderRepository.findById(id).get();
        } else {
            logger.error("Order with id {} not found", id);
        }
        return order;
    }

    @Operation(summary = "Add a new order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order added",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Order.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Order not added",
                    content = @Content) })
    @PostMapping("/add")
    public Order addOrder(@Parameter(description = "id of user to be added in the order") @RequestParam Long idUser, @Parameter(description = "id of book to be added in the order") @RequestParam Long idBook) {

        Order savedOrder = null;

        if (readerRepository.findById(idUser).isPresent() && bookRepository.findById(idBook).isPresent()) {

            Book book = bookRepository.findById(idBook).get();
            User reader = readerRepository.findById(idUser).get();

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

    @Operation(summary = "Delete an order by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Order.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Order not found",
                    content = @Content) })
    @DeleteMapping("/{id}/delete")
    public void deleteOrder(@Parameter(description = "id of order to be deleted") @PathVariable Long id) {

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
