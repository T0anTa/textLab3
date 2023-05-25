package com.example.textLab3.controller;


import com.example.textLab3.entity.Book;
import com.example.textLab3.services.BookServices;
import com.example.textLab3.services.CategoryService;
import jakarta.validation.Valid;
import org.hibernate.cfg.annotations.ListBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/books")

public class BookController {
    @Autowired
    private BookServices bookService;
    @Autowired
    private List<Book> books;
    @Autowired
    private CategoryService categoryService;



//    @GetMapping
//    public String listBooks(Model model){
//        model.addAttribute("books", books);
//        model.addAttribute("title","Book List");
//        return "book/list";
//    }

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

    @PostMapping("/add")
    public String addBook(@Valid @ModelAttribute("book") Book book, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("categories", categoryService.getAllCategories());
            return "book/add";
        }
        bookService.addBook(book);
        return "redirect:/books";
    }


    @GetMapping("/edit/{id}")
    public String editBookForm(@PathVariable("id") Long id, Model model){
        Optional<Book> editBook = books.stream()
               // .filter(book -> book.getId().equals(id))
                .findFirst();
        if (editBook.isPresent()){
            model.addAttribute("book", editBook.get());
            return "book/edit";
        }else {
            return "not-found";
        }
    }

    @PostMapping("/edit")
    public String editBook(@ModelAttribute("book") Book updatedBook){
        books.stream().filter(book -> book.getId() == updatedBook.getId()).findFirst().ifPresent(book -> books.set(books.indexOf(book),updatedBook));
        return "redirect:/books";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") Long id){
        books.removeIf(book -> book.getId().equals(id));
        return "redirect:/books";
    }
}
