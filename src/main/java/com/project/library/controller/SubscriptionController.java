package com.project.library.controller;

import com.project.library.Db.SubscriptionRepository;
import com.project.library.model.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @GetMapping("/list")
    public List<Subscription> getList() {
        return (List<Subscription>)subscriptionRepository.findAll();
    }

    @GetMapping("/{id}")
    public Subscription getSubscription(@PathVariable long id) { return subscriptionRepository.findById(id).get(); }

    @PostMapping("/add")
    public Subscription addSubscription(@RequestBody Subscription subscription) {
        return subscriptionRepository.save(subscription);
    }

    @PutMapping("/{id}/update")
    public Subscription updateSubscription(@PathVariable Long id, @RequestBody Subscription subscription) {
        subscription.setId(id);
        return subscriptionRepository.save(subscription);
    }
    @DeleteMapping("/{id}/delete")
    public void deleteSubscription(@PathVariable Long id) {
        subscriptionRepository.deleteById(id);

    }
}
