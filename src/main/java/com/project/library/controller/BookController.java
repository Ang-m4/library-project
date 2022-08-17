package com.project.library.controller;
import com.project.library.interfaceService.IBookService;
import com.project.library.interfaceService.IOrderService;
import com.project.library.interfaceService.IUserService;
import com.project.library.model.Book;
import com.project.library.model.Order;
import com.project.library.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping
public class BookController {
    @Autowired
    private IBookService BService;

    @GetMapping
    public String listBook(Model model) {
        List<Book> books= BService.listBook();
        model.addAttribute("books", books);
        return "index";
    }
}
