package com.project.library.controller;

import com.project.library.Db.BookRepository;
import com.project.library.Db.OrderRepository;
import com.project.library.Db.SubscriptionRepository;
import com.project.library.model.Book;
import com.project.library.model.Order;
import com.project.library.model.Reader;
import com.project.library.model.Subscription;

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
@RequestMapping("/subscription")
public class SubscriptionController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private BookRepository bookRepository;

    @Operation(summary = "Get the list of subscriptions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subscriptions listed",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Subscriptions not found",
                    content = @Content) })
    @GetMapping("/list")
    public List<Subscription> getList() {

        List<Subscription> list = (List<Subscription>) subscriptionRepository.findAll();

        if (list.size() == 0) {
            logger.error("No subscriptions found in the database");
        } else {
            logger.info("Getting subscriptions from database");
        }
        return list;
    }

    @Operation(summary = "Get a subscription by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subscription found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Subscription not found",
                    content = @Content) })
    @GetMapping("/{id}")
    public Subscription getSubscription(@Parameter(description = "id of subscription to be searched") @PathVariable long id) {

        Subscription subscription = null;
        if (subscriptionRepository.findById(id).isPresent()) {
            logger.info("Getting subscription with id {}", id);
            subscription = subscriptionRepository.findById(id).get();
        } else {
            logger.error("Subscription with id {} not found", id);
        }
        return subscription;
    }

    @Operation(summary = "Add a new subscription")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subscription added",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Subscription not added",
                    content = @Content) })
    @PostMapping("/add")
    public Subscription addSubscription(@Parameter(description = "id of order to be added in the subscription") @RequestParam Long idOrder, @Parameter(description = "duration(in days) to be added in the subscription") @RequestParam Long duration) {

        Subscription newSubscription = null;

        if (orderRepository.findById(idOrder).isPresent()) {

            Order order = orderRepository.findById(idOrder).get();

            Reader reader = order.getReader();
            Book book = order.getBook();
            LocalDate givenDate = LocalDate.now();
            LocalDate returnDate = givenDate.plusDays(duration);

            orderRepository.deleteById(order.getId());

            newSubscription = subscriptionRepository.save(new Subscription(-1, reader, book, givenDate, returnDate, 0));

            logger.info("Adding subscription with id {}", newSubscription.getId());
        } else {
            logger.error("Order with id {} not found", idOrder);
        }

        logger.info("Saving subscription with id {} to the database");

        return newSubscription;
    }

    @Operation(summary = "Update a subscription by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subscription updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Subscription not found",
                    content = @Content) })
    @PutMapping("/{id}/update")
    public Subscription updateSubscription(@Parameter(description = "id of subscription to be updated") @PathVariable Long id, @Parameter(description = "days to be added in the duration´s subscription") @RequestParam Long plus) {

        Subscription subscription = null;

        if (subscriptionRepository.findById(id).isPresent()) {

            subscription = subscriptionRepository.findById(id).get();
            subscription.setReturnDate(subscription.getReturnDate().plusDays(plus));
            subscriptionRepository.save(subscription);

            logger.info("Updating subscription with id {}", id);
        } else {
            logger.error("Subscription with id {} not found", id);
        }

        return subscription;
    }

    @Operation(summary = "Delete a subscription by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subscription deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Subscription not found",
                    content = @Content) })
    @DeleteMapping("/{id}/delete")
    public void deleteSubscription(@Parameter(description = "id of subscription to be deleted") @PathVariable Long id) {

        if (subscriptionRepository.findById(id).isPresent()) {
            Subscription subscription = subscriptionRepository.findById(id).get();
            Book book = subscription.getBook();

            subscriptionRepository.deleteById(subscription.getId());

            book.setCopies(book.getCopies() + 1);
            bookRepository.save(book);

            logger.info("Deleting subscription with id {}", id);
        } else {
            logger.error("Subscription with id {} not found", id);
        }

    }
}
