package com.project.library.interfaceService;

import com.project.library.model.Order;
import java.util.List;
import java.util.Optional;

public interface IOrderService {
    public List<Order>listOrder();
    public Optional<Order> searchIdOrder(long isbn);
    public int saveOrder(Order order);
    public void deleteOrder(long id);
}
