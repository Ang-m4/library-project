package com.project.library.controller;

import com.project.library.Db.UserRepository;
import com.project.library.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/list")
    public List<User> list() {
        List<User> users = (List<User>) userRepository.findAll();
        return users;
    }

    @GetMapping("/{id}")
    public User get(@PathVariable long id) {
        return userRepository.findById(id).get();
    }
}
