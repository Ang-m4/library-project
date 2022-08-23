package com.project.library.Db;

import org.springframework.data.repository.CrudRepository;

import com.project.library.model.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {

}
