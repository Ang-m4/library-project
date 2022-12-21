package com.project.library.controller;

import com.project.library.error.ErrorDTO;
import com.project.library.model.Order;
import com.project.library.service.OrderService;

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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    OrderService orderService;

    @Operation(summary = "Get the orders list filteded by userNickname or book title with a sort order")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Orders listed", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Order.class)) }),
        @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))) })
    @GetMapping("/list")
    public ResponseEntity<List<Order>> getList(
        @RequestParam(value = "nickname", required = false, defaultValue = "") String nickname,
        @RequestParam(value = "filter", required = false, defaultValue = "") String filter,
        @RequestParam(value = "limit", required = false, defaultValue = "100") Integer limit
    ) {
        List<Order> list = orderService.getAllOrders(nickname, filter, limit);
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Get an order by its id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Returns the order data given an order id", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Order.class)) }),
        @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
        @ApiResponse(responseCode = "404", description = "Order with given id not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))) })
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@Parameter(description = "id of order to be searched") @PathVariable long id) {
        Order order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    @Operation(summary = "Add a new order")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Order saved", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Order.class)) }),
        @ApiResponse(responseCode = "400", description = "Bad request, invalid patterns or lenght in parameters", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
        @ApiResponse(responseCode = "406", description = "Book out of stock or blocked user", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))) })
    @PostMapping("/add")
    public ResponseEntity<Order> addOrder(
        @Parameter(description = "id of user to be added in the order") @RequestParam Long idUser, 
        @Parameter(description = "isbn of book to be added in the order") @RequestParam String isbn) {
        Order savedOrder = orderService.addOrder(idUser, isbn);
        logger.info("Saving order {} to the database",savedOrder.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
    }

    @Operation(summary = "Delete an order by its id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Order deleted", content = @Content),
        @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
        @ApiResponse(responseCode = "404", description = "Order not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))) })
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteOrder(@Parameter(description = "id of order to be deleted") @PathVariable Long id) {
        orderService.deleteOrder(id);
        logger.info("Deleting order with id {}",id);
        return ResponseEntity.ok("Order with id:" + id + " deleted");
    }

}
