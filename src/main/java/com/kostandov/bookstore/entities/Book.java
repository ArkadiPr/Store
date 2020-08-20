package com.kostandov.bookstore.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
public class Book {
    @AllArgsConstructor
    @Getter
    public enum Genre {
        FANTASY("Фэнтези"), FICTION("Фантастика"), DETECTIVE("Детектив");

        private String rus;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "publish_year")
    private int publishYear;

    @Column(name = "genre")
    @Enumerated(EnumType.STRING)
    private Genre genre;
}
