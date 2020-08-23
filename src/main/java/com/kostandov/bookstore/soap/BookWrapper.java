package com.kostandov.bookstore.soap;

import com.kostandov.ws.bookstore.Book;
import com.kostandov.ws.bookstore.Genre;

import java.util.ArrayList;
import java.util.List;

public class BookWrapper {

    public static Book wrapBook(com.kostandov.bookstore.entities.Book book){
        Book wsBook=new Book();
        wsBook.setId(book.getId().intValue());
        wsBook.setDescription(book.getDescription());
        wsBook.setPrice(book.getPrice().intValue());
        switch(book.getGenre()){
            case FANTASY:
                wsBook.setGenre(Genre.FANTASY);
                break;
            case FICTION:
                wsBook.setGenre(Genre.FICTION);
                break;
            case DETECTIVE:
                    wsBook.setGenre(Genre.DETECTIVE);
                    break;
        }
        wsBook.setPublishYear(book.getPublishYear());
        wsBook.setTitle(book.getTitle());
        return wsBook;
    }

    public static List<Book> wrapBooks(List<com.kostandov.bookstore.entities.Book> books){
        List<Book> wsBooks=new ArrayList<>();
        for(com.kostandov.bookstore.entities.Book book:books){
            wsBooks.add(wrapBook(book));
        }
        return wsBooks;
    }
}
