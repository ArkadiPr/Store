package com.kostandov.bookstore.services;

import com.kostandov.bookstore.entities.Book;
import com.kostandov.bookstore.entities.Order;
import com.kostandov.bookstore.exceptions.ResourceNotFoundException;
import com.kostandov.bookstore.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private OrderRepository orderRepository;

    @Autowired
    public void setOrdersRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order findById(Long id){
        return orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order with id: " + id + " not found"));
    }

    public Order updateOrderStatus(Long id,Order.Status status){
        Order order=findById(id);
        order.setStatus(status);
        saveOrder(order);
        return order;
    }


}
