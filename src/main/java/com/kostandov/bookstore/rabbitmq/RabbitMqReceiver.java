package com.kostandov.bookstore.rabbitmq;

import com.kostandov.bookstore.entities.Order;
import com.kostandov.bookstore.exceptions.ResourceNotFoundException;
import com.kostandov.bookstore.services.OrderService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqReceiver {
    private RabbitTemplate rabbitTemplate;

    private static final String ORDER_READY="Order ready:";

    @Autowired
    private OrderService orderService;

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void receiveMessage(byte[] message) {
        String messageText=new String(message);
        System.out.println("Received: <" + messageText + ">");
        int res=checkMessage(messageText);
        try {
            orderService.updateOrderStatus((long) res, Order.Status.IS_DONE);
        }catch (ResourceNotFoundException e){
            System.out.println("Order with id: " + res + " not found");
        }

    }
    public int checkMessage(String message){
            try {
                Integer res=Integer.parseInt(message.substring(message.indexOf(ORDER_READY)+ORDER_READY.length()));
                return res;
            }catch (Exception e) {
                throw new RuntimeException("Illegal Command!");
            }
    }
}