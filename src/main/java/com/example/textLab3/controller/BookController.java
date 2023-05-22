package com.example.textLab3.controller;


import com.example.textLab3.entity.Book;
import com.example.textLab3.services.BookServices;
import com.example.textLab3.services.CategoryService;
import org.hibernate.cfg.annotations.ListBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/books")

public class BookController {
    @Autowired
    private BookServices bookService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String showAllBooks(Model model){
        List<Book> books=bookService.getAllBooks();
        model.addAttribute("books",books);
        return "book/list";
    }

    @GetMapping("/add")
    public String addBookForm(Model model){
        model.addAttribute("book", new Book());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "book/add";
    }

    @PostMapping("/add")
    public String addBook(@ModelAttribute("book")Book book){
        bookService.addBook(book);
        return "redirect:/books";
    }
}
