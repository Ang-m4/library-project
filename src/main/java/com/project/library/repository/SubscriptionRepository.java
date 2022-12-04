package com.project.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.project.library.model.Subscription;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends CrudRepository<Subscription, Long> {

    @Query("SELECT s FROM Subscription s WHERE s.book.isbn = ?1")
    List<Subscription> findByBookIsbn(Long isbn);

    @Query("SELECT s FROM Subscription s WHERE s.user.id = ?1")
    List<Subscription> findAByReaderId(Long id);

}
