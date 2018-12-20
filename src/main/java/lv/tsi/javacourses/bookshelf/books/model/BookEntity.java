package lv.tsi.javacourses.bookshelf.books.model;

import lv.tsi.javacourses.bookshelf.auth.model.Role;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "Book")
@Table(name = "books")
public class BookEntity implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "book_title", length = 200, nullable = false)
    private String title;
    @Column(name = "book_isbn", length = 50, unique = true, nullable = false)
    private String isbn;
    @Column(name = "book_author", length = 100, nullable = false)
    private String author;
    @Column(name = "book_year", nullable = false)
    private int year;
    @Column(name = "book_description", length = 2000)
    private int description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDescription() {
        return description;
    }

    public void setDescription(int description) {
        this.description = description;
    }
}
