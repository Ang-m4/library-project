package com.project.library.Db;
import org.springframework.data.repository.CrudRepository;
import com.project.library.model.Order;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {

}
