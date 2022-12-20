package com.project.library.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.library.model.Book;
import com.project.library.model.Order;
import com.project.library.model.User;
import com.project.library.repository.BookRepository;
import com.project.library.repository.OrderRepository;
import com.project.library.repository.UserRepository;

@Service
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookRepository bookRepository;

    public List<Order> getAllOrders(String userNickname,String filter, Integer limit){
        List<Order> nonFilteredOrders = (List<Order>) orderRepository.findAll();
        if (!userNickname.equals("")) {
            nonFilteredOrders = nonFilteredOrders.stream()
                    .filter(order -> order.getUser().getNickname().toLowerCase().contains(userNickname))
                    .collect(Collectors.toList());
        }
        if (!filter.equals("")) {
            nonFilteredOrders = nonFilteredOrders.stream()
                    .filter(order -> order.getBook().getTitle().toLowerCase().contains(filter))
                    .collect(Collectors.toList());
        }
        return nonFilteredOrders.stream().limit(limit).toList();
    }

    public Order getOrderById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isEmpty()) {
            //TODO throw exception if order not found
            return null;
        }
        return order.get();
    }

    public Order addOrder(Long idUser,String isbn) {
        Optional<User> user = userRepository.findById(idUser);
        Optional<Book> book = bookRepository.findByISBN(isbn);
        if(user.isEmpty() || book.isEmpty()){
            //TODO throw exception if arguments not valid
            return null;
        }
        if(book.get().getCopies() <= 0){
            //TODO throw exception if book not avaliable
            return null;
        }
        if(user.get().getRole() != "ROLE_USER" || user.get().isBlocked()){
            //TODO throw exception if user cannot order books
            return null;
        }
        Order newOrder = Order.builder()
            .book(book.get())
            .user(user.get())
            .requestDate(LocalDate.now())
            .build();
        Book orderedBook = book.get();
        orderedBook.setCopies(orderedBook.getCopies() - 1);
        bookRepository.save(orderedBook);
        return orderRepository.save(newOrder);
    }

    public void deleteOrder(Long id){
        Optional<Order> orderToDelete = orderRepository.findById(id);
        if(orderToDelete.isEmpty()){
            //TODO throw exception if order does not exist
            return;
        }
        Book book = orderToDelete.get().getBook();
        book.setCopies(book.getCopies() + 1);
        bookRepository.save(book);
        orderRepository.delete(orderToDelete.get());
    }

}
