package com.project.library.controller;

import com.project.library.Db.UserRepository;
import com.project.library.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/add")
    public User addUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @PutMapping("/{id}/update")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        return userRepository.save(user);
    }
    @DeleteMapping("/{id}/delete")
    public void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);

    }
}
