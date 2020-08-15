package com.kostandov.bookstore.controllers;


import com.kostandov.bookstore.beans.Cart;
import com.kostandov.bookstore.entities.Book;
import com.kostandov.bookstore.services.BookService;
import com.kostandov.bookstore.utils.BookFilter;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
//@RequestMapping("/books")
@AllArgsConstructor
public class BookController {
    private BookService bookService;

    private Cart cart;


    @GetMapping({"/books/{id}","/books"})
    public String showAllBooks(Model model,
                               @RequestParam(name = "p", defaultValue = "1") Integer pageIndex,
                               @RequestParam MultiValueMap<String, String> params,
                               @PathVariable(required = false) Long id
    ) {
        if(id!=null){
            cart.addBookToCart(bookService.findById(id));
        }
        BookFilter bookFilter = new BookFilter(params);
        Page<Book> page = bookService.findAll(bookFilter.getSpec(), pageIndex - 1, 5);
        model.addAttribute("booksPage", page);
        model.addAttribute("filterDef", bookFilter.getFilterParams());
        return "store-page";
    }




/*    @GetMapping(value = "/addtocart/{id}")
    public String addToCart(@PathVariable Long id) {
        cart.addBookToCart(bookService.findById(id));
        return "redirect:/books";
    }*/

    // Эта часть кода будет сильно скорректирована после темы Spring REST
    @GetMapping("/rest")
    @ResponseBody
    @CrossOrigin("*")
    public List<Book> getAllBooks() {
        return bookService.findAll();
    }
}
