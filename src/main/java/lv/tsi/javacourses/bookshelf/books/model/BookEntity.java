package lv.tsi.javacourses.bookshelf.books.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity(name = "Book")
@Table(name = "books")
public class BookEntity implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @Size (min = 1, max = 200)
    @NotEmpty (message = "Enter a valid book title.")
    @Column(name = "book_title", length = 200, nullable = false)
    private String title;

    @Size (min = 1, max = 50)
    @NotEmpty (message = "Enter a valid ISBN.")
    @Column(name = "book_isbn", length = 50, unique = true, nullable = false)
    private String isbn;

    @Size (min = 1, max = 100)
    @NotEmpty (message = "Enter a valid book author.")
    @Column(name = "book_author", length = 100, nullable = false)
    private String author;

    @NotNull (message = "Enter a year between 1000 and this year.")
    @Min(value = 1000)
    @Column(name = "book_year", nullable = false)
    private int year;

    @Size (max = 2000, message = "No more than 2000 symbols.")
    @Column(name = "book_description", length = 2000)
    private String description;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
