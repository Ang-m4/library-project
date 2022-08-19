package com.project.library.service;

import com.project.library.interfaceService.IOrderService;
import com.project.library.Db.OrderRepository;
import com.project.library.model.Order;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class OrderService implements IOrderService {
    @Autowired
    private OrderRepository dataOrder;
    @Override
    public List<Order> listOrder() {
        return (List<Order>)dataOrder.findAll();
    }

    @Override
    public Optional<Order> searchIdOrder(long id) {
        return null;
    }

    @Override
    public int saveOrder(Order o) {
        return 0;
    }

    @Override
    public void deleteOrder(long id) {
    }
}

