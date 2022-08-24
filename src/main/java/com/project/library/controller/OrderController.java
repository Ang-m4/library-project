package com.project.library.controller;

import com.project.library.Db.OrderRepository;
import com.project.library.model.Order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private OrderRepository orderRepository;

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
    public Order addOrder(@RequestBody Order order) {

        Order newOrder = orderRepository.save(order);
        logger.info("Saving order with id {} to the database", newOrder.getId());

        return orderRepository.save(order);
    }

    @PutMapping("/{id}/update")
    public Order updateOrder(@PathVariable Long id, @RequestBody Order order) {
        order.setId(id);
        Order updatedOrder = orderRepository.save(order);
        logger.info("Updating order {} , and sending to the database",updatedOrder.getId());
        return orderRepository.save(order);
    }

    @DeleteMapping("/{id}/delete")
    public void deleteOrder(@PathVariable Long id) {

        if (orderRepository.findById(id).isPresent()) {
            logger.info("Deleting order with id {}", id);
            orderRepository.deleteById(id);
        } else {
            logger.error("Order with id {} not found", id);
        }
        
    }

}
