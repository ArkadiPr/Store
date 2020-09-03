package com.kostandov.bookstore.rest_controllers_test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.kostandov.bookstore.configs.SecurityConfig;
import com.kostandov.bookstore.controllers.rest.BookRestController;
import com.kostandov.bookstore.entities.Book;
import com.kostandov.bookstore.services.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = BookRestController.class,excludeAutoConfiguration = SecurityAutoConfiguration.class,
excludeFilters = {@ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE, value= SecurityConfig.class)})
public class BookRestControllerTest {

    @MockBean
    private BookService bookService;

    @Autowired
    private MockMvc mockMvc;

    private List<Book> books;

    ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void init(){
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
        books= Arrays.asList(book1,book2);
    }

    @Test
    public void getAllBooksTest() throws Exception {
        when(bookService.findAll()).thenReturn(books);
        mockMvc.perform(get("/api/v1/books")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void getBookByIdTest() throws Exception {
        when(bookService.findById(1l)).thenReturn(books.get(0));
        mockMvc.perform(get("/api/v1/books/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());
        verify(bookService).findById(1l);
    }

    @Test
    public void deleteAllBooksTest() throws Exception {
        mockMvc.perform(delete("/api/v1/books")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(bookService).deleteAll();
    }

    @Test
    public void deleteByIdTest() throws Exception {
        mockMvc.perform(delete("/api/v1/books/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(bookService).deleteById(2l);
    }

    @Test
    public void modifyBookTest() throws Exception {

        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(books.get(0));

        when(bookService.existsById(1l)).thenReturn(true);


        mockMvc.perform(put("/api/v1/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk());
        verify(bookService).saveOrUpdate(books.get(0));
    }




}
