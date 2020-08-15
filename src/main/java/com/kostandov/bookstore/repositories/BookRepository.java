package com.kostandov.bookstore.repositories;


import com.kostandov.bookstore.entities.Book;
import com.kostandov.bookstore.entities.dto.BookDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    List<BookDto> findAllBy();
}
