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

    @Enumerated(EnumType.STRING)
    @Column(name="genre")
    private Genre genre;

    @OneToMany
    private List<OrderItem> orderItems;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return publishYear == book.publishYear &&
                Objects.equals(id, book.id) &&
                Objects.equals(title, book.title) &&
                Objects.equals(description, book.description) &&
                Objects.equals(price, book.price) &&
                genre == book.genre;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, price, publishYear, genre);
    }
}
