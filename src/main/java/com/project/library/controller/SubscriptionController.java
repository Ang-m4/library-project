package com.project.library.controller;

import com.project.library.Db.SubscriptionRepository;
import com.project.library.model.Subscription;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @GetMapping("/list")
    public List<Subscription> getList() {

        List<Subscription> list = (List<Subscription>) subscriptionRepository.findAll();

        if(list.size() == 0){
            logger.error("No subscriptions found in the database");
        }
        else{
            logger.info("Getting subscriptions from database");
        }
        return list;
    }

    @GetMapping("/{id}")
    public Subscription getSubscription(@PathVariable long id) {

        Subscription subscription = null;
        if(subscriptionRepository.findById(id).isPresent()){
            logger.info("Getting subscription with id {}", id);
            subscription = subscriptionRepository.findById(id).get();
        }
        else{
            logger.error("Subscription with id {} not found", id);
        }
        return subscription;
    }

    @PostMapping("/add")
    public Subscription addSubscription(@RequestBody Subscription subscription) {

        Subscription newSubscription = subscriptionRepository.save(subscription);
        logger.info("Saving subscription with id {} to the database", newSubscription.getId());
        
        return newSubscription;
    }

    @PutMapping("/{id}/update")
    public Subscription updateSubscription(@PathVariable Long id, @RequestBody Subscription subscription) {

        subscription.setId(id);
        Subscription newSubscription = subscriptionRepository.save(subscription);
        logger.info("Updating subscription {} , and sending to the database", newSubscription.getId());

        return newSubscription;
    }

    @DeleteMapping("/{id}/delete")
    public void deleteSubscription(@PathVariable Long id) {

        if(subscriptionRepository.findById(id).isPresent()){
            subscriptionRepository.deleteById(id);
            logger.info("Deleting subscription with id {}", id);
        }
        else{
            logger.error("Subscription with id {} not found", id);
        }

    }
}
