package com.kostandov.bookstore.services;

import com.kostandov.bookstore.entities.Order;
import com.kostandov.bookstore.entities.OrderItem;
import com.kostandov.bookstore.repositories.OrderItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderItemService {

    private OrderItemRepository orderItemRepository;

    public OrderItem saveOrUpdate(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

}
