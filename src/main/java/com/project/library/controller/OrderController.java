package com.project.library.controller;
import com.project.library.Db.OrderRepository;
import com.project.library.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;

    @GetMapping
    public String listOrder(Model model) {
        List<Order> orders= (List<Order>) orderRepository.findAll();
        model.addAttribute("orders", orders);
        return "index";
    }
}
