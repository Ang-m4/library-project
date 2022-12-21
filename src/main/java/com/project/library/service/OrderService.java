package com.project.library.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.project.library.error.RequestException;
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
            throw new RequestException(HttpStatus.NOT_FOUND, "Order with id " + id + "was not found");
        }
        return order.get();
    }

    public Order addOrder(Long idUser,String isbn) {
        Optional<User> user = userRepository.findById(idUser);
        Optional<Book> book = bookRepository.findByISBN(isbn);
        if(user.isEmpty() || book.isEmpty()){
            throw new RequestException(HttpStatus.BAD_REQUEST, "Invalid Arguments either user or book not exist");
        }
        if(book.get().getCopies() <= 0){
            throw new RequestException(HttpStatus.NOT_ACCEPTABLE, "Book with isbn " + isbn + "is out of stock");
        }
        if(user.get().getRole() != "ROLE_USER" || user.get().isBlocked()){
            throw new RequestException(HttpStatus.NOT_ACCEPTABLE, "Invalid arguments, either user block or not a reader");
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
            throw new RequestException(HttpStatus.NOT_FOUND, "Order with id " + id + " does not exist");
        }
        Book book = orderToDelete.get().getBook();
        book.setCopies(book.getCopies() + 1);
        bookRepository.save(book);
        orderRepository.delete(orderToDelete.get());
    }

}
