package com.project.library.interfaces;
import org.springframework.data.repository.CrudRepository;
import com.project.library.model.Order;

public interface IOrder extends CrudRepository<Order, Long> {


}
