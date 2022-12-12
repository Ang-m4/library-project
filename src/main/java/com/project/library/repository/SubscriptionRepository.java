package com.project.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.project.library.model.Subscription;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends CrudRepository<Subscription, Long> {

    @Query(value = "SELECT * FROM subscriptions s WHERE s.book_isbn = ?1", nativeQuery = true)
    List<Subscription> findByBookIsbn(String isbn);

    @Query(value = "SELECT * FROM subscriptions s WHERE s.user_id = ?1", nativeQuery = true)
    List<Subscription> findAByUserId(Long id);

    @Query(value = "SELECT * FROM subscriptions s ORDER BY s.subscription_return_date ?1", nativeQuery = true)
    List<Subscription> findAll(String sortOrder);

}
