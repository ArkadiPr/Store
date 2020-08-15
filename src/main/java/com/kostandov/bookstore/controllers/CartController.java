package com.kostandov.bookstore.controllers;

import com.kostandov.bookstore.beans.Cart;
import com.kostandov.bookstore.entities.Book;
import com.kostandov.bookstore.entities.Order;
import com.kostandov.bookstore.entities.OrderItem;
import com.kostandov.bookstore.services.OrderItemService;
import com.kostandov.bookstore.services.OrderService;
import com.kostandov.bookstore.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cart")
@AllArgsConstructor
public class CartController {

    Cart cart;

    OrderItemService orderItemService;

    OrderService orderService;

    UserService userService;




    @GetMapping
    public String showAllOrders(Model model){
        HashMap<Book, Integer> allOrders=cart.getBooksCart();
        model.addAttribute("allOrders",allOrders.entrySet());

        return "cart-page";
    }

    @GetMapping("/saveOrder")
    public String  saveAllOrders(){
        User currentUser=(User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        com.kostandov.bookstore.entities.User loggedInUser=userService.findByUsername(currentUser.getUsername()).
                orElseThrow(()->new UsernameNotFoundException("No Such user"));

        HashMap<Book, Integer> allOrders=cart.getBooksCart();
        Order order=new Order();
        List<OrderItem> orderItemList=new ArrayList<>();

        for(Map.Entry<Book,Integer> orderBook:allOrders.entrySet()){
            OrderItem orderItem=new OrderItem();
            orderItem.setBook(orderBook.getKey());
            orderItem.setCount(orderBook.getValue());
            orderItem.setPrice(orderBook.getKey().getPrice().intValue()*orderBook.getValue());

            orderItemList.add(orderItem);
            orderItemService.saveOrUpdate(orderItem);
        }
        order.setOrderItems(orderItemList);
        order.setUser(loggedInUser);
        orderService.saveOrUpdate(order);

        return "redirect:/books";
    }




}
