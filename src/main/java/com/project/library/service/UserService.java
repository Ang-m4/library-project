package com.project.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        if (!user.isPresent()) {
            // TODO throw exception
        }
        return user.get();
    }

    public User saveUser(User user) {
        if (userRepository.findById(user.getId()).isPresent()) {
            // TODO throw exception if user already exists
            return null;
        }
        if (userRepository.findByNickname(user.getNickname()).isPresent()) {
            // TODO throw exception if nickname is already in use
            return null;
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
                // TODO throw exception if user is in use
            }
        } else {
            // TODO throw exception if user does not exist
        }
    }

    public User updateUser(User user) {
        if (!userRepository.findById(user.getId()).isPresent()) {
            // TODO throw exception if user does not exist
            return null;
        } 
        if(userRepository.findByNickname(user.getNickname()).isPresent()){
            if(!userRepository.findByNickname(user.getNickname()).get().getId().equals(user.getId())){
                //TODO throw exception if nickname is already in use
                return null;
            }
        }
        User updatedUser = userRepository.save(user);
        return updatedUser;
    }

}
