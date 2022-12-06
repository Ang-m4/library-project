package com.project.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.project.library.model.Order;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {

    @Query(value = "SELECT * FROM orders o WHERE o.book_isbn = ?1", nativeQuery = true)
    List<Order> findByBookIsbn(String isbn);

    @Query(value =  "SELECT * FROM orders o WHERE o.user_id = ?1", nativeQuery = true)
    List<Order> findByUserId(Long id);
}
