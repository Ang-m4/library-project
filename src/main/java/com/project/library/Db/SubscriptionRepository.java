package com.project.library.Db;

import org.springframework.data.repository.CrudRepository;

import com.project.library.model.Subscription;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends CrudRepository<Subscription, Long> {

}
