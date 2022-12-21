package com.project.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.project.library.error.RequestException;
import com.project.library.model.User;
import com.project.library.repository.OrderRepository;
import com.project.library.repository.SubscriptionRepository;
import com.project.library.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    SubscriptionRepository subscriptionRepository;

    public List<User> getAllUsers(String filter) {
        List<User> nonFilteredUsers = (List<User>) userRepository.findAll();
        if (!filter.equals(""))
            return nonFilteredUsers.stream()
                    .filter(user -> user.getName().toLowerCase().contains(filter.toLowerCase())
                            || user.getLastname().toLowerCase().contains(filter.toLowerCase())
                            || user.getNickname().toLowerCase().contains(filter.toLowerCase()))
                    .toList();
        return nonFilteredUsers;
    }

    public User findUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new RequestException(HttpStatus.NOT_FOUND, "User with id " + id + " not found");
        }
        return user.get();
    }

    public User saveUser(User user) {
        if (userRepository.findById(user.getId()).isPresent()) {
            throw new RequestException(HttpStatus.NOT_FOUND, "User with id " + user.getId() + " already exist");
        }
        if (userRepository.findByNickname(user.getNickname()).isPresent()) {
            throw new RequestException(HttpStatus.NOT_ACCEPTABLE, "Nickname '" + user.getNickname() + "' already in use");
        }
        User savedUser = userRepository.save(user);
        return savedUser;
    }

    public void deleteUser(Long id) {
        if (userRepository.findById(id).isPresent()) {
            if (orderRepository.findByUserId(id).isEmpty()
                    && subscriptionRepository.findAByUserId(id).isEmpty()) {
                userRepository.deleteById(id);
            } else {
                throw new RequestException(HttpStatus.NOT_ACCEPTABLE, "User with id " + id + " has a subscription asociated");
            }
        } else {
            throw new RequestException(HttpStatus.NOT_FOUND, "User with id " + id + " not found");
        }
    }

    public User updateUser(User user) {
        if (!userRepository.findById(user.getId()).isPresent()) {
            throw new RequestException(HttpStatus.NOT_FOUND, "User with id " + user.getId() + " not found");
        } 
        if(userRepository.findByNickname(user.getNickname()).isPresent()){
            if(!userRepository.findByNickname(user.getNickname()).get().getId().equals(user.getId())){
                throw new RequestException(HttpStatus.NOT_ACCEPTABLE, "Nickname '" + user.getNickname() + "' already in use");
            }
        }
        User updatedUser = userRepository.save(user);
        return updatedUser;
    }

}
