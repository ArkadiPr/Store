package com.kostandov.bookstore.soap;


import com.kostandov.bookstore.entities.Book;
import com.kostandov.bookstore.services.BookService;
import com.kostandov.ws.bookstore.GetBookRequest;
import com.kostandov.ws.bookstore.GetBookResponse;
import com.kostandov.ws.bookstore.GetBooksRequest;
import com.kostandov.ws.bookstore.GetBooksResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;


@Endpoint
public class BooksEndPoint {

    private static final String NAMESPACE_URI="http://www.kostandov.com/ws/bookstore";

    private BookService bookService;

    @Autowired
    public BooksEndPoint(BookService bookService) {
        this.bookService = bookService;
    }
    @PayloadRoot(namespace = NAMESPACE_URI,localPart = "getBookRequest")
    @ResponsePayload
    public GetBookResponse getBook(@RequestPayload GetBookRequest request){
        GetBookResponse response=new GetBookResponse();
        Book curBook=bookService.findById(new Long(request.getId()));
        response.setBook(BookWrapper.wrapBook(curBook));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI,localPart = "getBooksRequest")
    @ResponsePayload
    public GetBooksResponse getBooks(@RequestPayload GetBooksRequest request){
        GetBooksResponse response=new GetBooksResponse();
        List<Book> curBooks=bookService.findAll();
        response.getBook().addAll(BookWrapper.wrapBooks(curBooks));
        return response;
    }
}
