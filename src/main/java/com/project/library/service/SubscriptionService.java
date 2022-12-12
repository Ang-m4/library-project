package com.project.library.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.library.model.Order;
import com.project.library.model.Subscription;
import com.project.library.repository.BookRepository;
import com.project.library.repository.OrderRepository;
import com.project.library.repository.SubscriptionRepository;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private BookRepository bookRepository;

    public List<Subscription> getAllSubscriptions(String userNickName, String bookTitle, String sortOrder, Integer limit) {
        List<Subscription> nonFilteredSubscriptions = (List<Subscription>) subscriptionRepository.findAll(sortOrder);
        if (!userNickName.equals("")) {
            nonFilteredSubscriptions = nonFilteredSubscriptions.stream()
                    .filter(subscription -> subscription.getUser().getNickname().toLowerCase().contains(userNickName))
                    .collect(Collectors.toList());
        }
        if (!bookTitle.equals("")) {
            nonFilteredSubscriptions = nonFilteredSubscriptions.stream()
                    .filter(subscription -> subscription.getBook().getTitle().toLowerCase().contains(bookTitle))
                    .collect(Collectors.toList());
        }
        return nonFilteredSubscriptions.stream().limit(10).toList();
    }

    public Subscription getSubscriptionById(Long id) {
        Optional<Subscription> subscription = subscriptionRepository.findById(id);
        if (!subscription.isPresent()) {
            //TODO throw exception if subscription not found
            return null;
        }
        return subscription.get();
    }

    public Subscription addSubscription(Long idOrder, long DaysTime) {
        Optional<Order> order = orderRepository.findById(idOrder);
        if (order.isEmpty()) {
            //TODO throw exception if order not found
            return null;
        }
        Subscription newSubscription = Subscription.builder()
            .user(order.get().getUser())
            .book(order.get().getBook())
            .givenDate(LocalDate.now())
            .returnDate(LocalDate.now().plusDays(DaysTime))
            .fine(0)
            .build();
        newSubscription.getBook().setCopies(newSubscription.getBook().getCopies() - 1);
        bookRepository.save(newSubscription.getBook());
        orderRepository.delete(order.get());
        return subscriptionRepository.save(newSubscription);
    }

    public Subscription updateSubscription(long id, long DaysPlus) {
        Optional<Subscription> subscription = subscriptionRepository.findById(id);
        if (subscription.isEmpty()) {
            //TODO throw exception if subscription not found
            return null;
        }
        Subscription updatedSubscription = subscription.get();
        updatedSubscription.setReturnDate(updatedSubscription.getReturnDate().plusDays(DaysPlus));
        return subscriptionRepository.save(updatedSubscription);
    }

    public void deleteSubscription(long id) {
        Optional<Subscription> subscription = subscriptionRepository.findById(id);
        if (subscription.isEmpty()) {
            //TODO throw exception if subscription not found
            return;
        }
        subscription.get().getBook().setCopies(subscription.get().getBook().getCopies() + 1);
        bookRepository.save(subscription.get().getBook());
        subscriptionRepository.delete(subscription.get());
    }

}
