package com.kostandov.bookstore.cart_test;

import com.kostandov.bookstore.beans.Cart;
import com.kostandov.bookstore.entities.Book;
import com.kostandov.bookstore.entities.Order;
import com.kostandov.bookstore.entities.OrderItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CartTest {

    private Cart cart;

    @Autowired
    public CartTest(Cart cart) {
        this.cart = cart;
    }

    @BeforeEach
    void init(){
        Book book1=new Book();
        book1.setId(1l);
        book1.setDescription("Description 1");
        book1.setGenre(Book.Genre.DETECTIVE);
        book1.setPrice(BigDecimal.valueOf(500));
        book1.setPublishYear(2002);

        Book book2=new Book();
        book2.setId(2l);
        book2.setDescription("Description 2");
        book2.setGenre(Book.Genre.DETECTIVE);
        book2.setPrice(BigDecimal.valueOf(700));
        book2.setPublishYear(2000);

        OrderItem orderItem1=new OrderItem();
        orderItem1.setId(1l);
        orderItem1.setBook(book1);
        orderItem1.setPrice(BigDecimal.valueOf(1500));
        orderItem1.setQuantity(3);

        OrderItem orderItem2=new OrderItem();
        orderItem2.setId(2l);
        orderItem2.setBook(book2);
        orderItem2.setPrice(BigDecimal.valueOf(1400));
        orderItem2.setQuantity(1);

        List<OrderItem> items=new ArrayList();
        items.add(orderItem1);
        items.add(orderItem2);
        cart.setItems(items);

    }

    @Test
    void testCartClear(){
        cart.clear();
        assertEquals(0,cart.getItems().size());
    }

    @Test
    void testCartAddExistingBook(){
        Book book1=cart.getItems().get(0).getBook();
        cart.add(book1);
        assertEquals(4,cart.getItems().get(0).getQuantity());
    }

    @Test
    void testCartAddNewBook(){
        Book book1=new Book();
        book1.setId(3l);
        book1.setDescription("Description 3");
        book1.setGenre(Book.Genre.DETECTIVE);
        book1.setPrice(BigDecimal.valueOf(300));
        book1.setPublishYear(2005);
        cart.add(book1);
        assertEquals(3,cart.getItems().size());
    }

    @Test
    void testCartDecrementExistingBook(){
        Book book1=cart.getItems().get(0).getBook();
        cart.decrement(book1);
        assertEquals(2,cart.getItems().get(0).getQuantity());
    }

    @Test
    void testCartDecrementLastBook(){
        Book lastBook=cart.getItems().get(1).getBook();
        cart.decrement(lastBook);
        assertFalse(cart.getItems().contains(lastBook));
    }

    @Test
    void testCartRemoveByProductId(){
        Book lastBook=cart.getItems().get(1).getBook();
        cart.removeByProductId(2l);
        assertFalse(cart.getItems().contains(lastBook));

    }

}
