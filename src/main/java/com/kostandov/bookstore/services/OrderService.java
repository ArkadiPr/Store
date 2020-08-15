package com.kostandov.bookstore.services;

import com.kostandov.bookstore.entities.Book;
import com.kostandov.bookstore.entities.Order;
import com.kostandov.bookstore.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderService {

    private OrderRepository orderRepository;

    public Order saveOrUpdate(Order order) {
        return orderRepository.save(order);
    }
}
