package com.project.library.Db;

import org.springframework.data.repository.CrudRepository;

import com.project.library.model.Subscription;

public interface SubscriptionRepository extends CrudRepository<Subscription, Long> {

}
