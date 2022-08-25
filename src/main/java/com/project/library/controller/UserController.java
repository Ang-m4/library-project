package com.project.library.controller;

import com.project.library.Db.UserRepository;
import com.project.library.model.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/list")
    public List<User> list() {
        List<User> users = (List<User>) userRepository.findAll();

        if (users.size() == 0) {
            logger.error("No users found in the database");
        } else {
            logger.info("Getting users from database");
        }
        return users;
    }

    @GetMapping("/{id}")
    public User get(@PathVariable long id) {

        User user = null;
        
        if (userRepository.findById(id).isPresent()) {
            logger.info("Getting user with id {}", id);
            user = userRepository.findById(id).get();
        } else {
            logger.error("User with id {} not found", id);
        }
        return user;
    }

    @PostMapping("/add")
    public User addUser(@RequestBody User user) {

        User newUser = userRepository.save(user);
        logger.info("Saving user {} to the database", newUser.getId());
        
        return newUser;
    }

    @PutMapping("/{id}/update")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        User updatedUser = userRepository.save(user);
        logger.info("Updating user {} , and sending to the database", id);

        return updatedUser;
    }

    @DeleteMapping("/{id}/delete")
    public void deleteUser(@PathVariable Long id) {

        if (userRepository.findById(id).isPresent()) {
            logger.info("Deleting user with id {}", id);
            userRepository.deleteById(id);
        } else {
            logger.error("User with id {} not found", id);
        }
    }
}
