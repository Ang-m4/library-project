package com.project.library.controller;

import com.project.library.Db.UserRepository;
import com.project.library.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String listUser(Model model) {
        List<User> users= (List<User>)userRepository.findAll();
        model.addAttribute("users", users);
        return "index";
    }

}
