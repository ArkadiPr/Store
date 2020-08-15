package com.kostandov.bookstore.beans;

import com.kostandov.bookstore.entities.Book;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;

@Component
@Data
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Cart {

    private HashMap<Book,Integer> booksCart;

    public Cart() {
        booksCart=new HashMap<>();
    }

    public void addBookToCart(Book book){
        if(booksCart.containsKey(book)){
            booksCart.put(book,booksCart.get(book)+1);
        }else {
            booksCart.put(book,1);
        }
    }

}
