package com.project.library.controller;

import com.project.library.Db.OrderRepository;
import com.project.library.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/list")
    public List<Order> getList() {
        return (List<Order>)orderRepository.findAll();
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable long id) { return orderRepository.findById(id).get(); }

    @PostMapping("/add")
    public Order addOrder(@RequestBody Order order) {
        return orderRepository.save(order);
    }

    @PutMapping("/{id}/update")
    public Order updateOrder(@PathVariable Long id, @RequestBody Order order) {
        order.setId(id);
        return orderRepository.save(order);
    }
    @DeleteMapping("/{id}/delete")
    public void deleteOrder(@PathVariable Long id) {
        orderRepository.deleteById(id);
    }

}
