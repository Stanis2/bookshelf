package lv.tsi.javacourses.bookshelf.books.boundary;

import lv.tsi.javacourses.bookshelf.books.model.BookEntity;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;

@ViewScoped
@Named
public class SingleBookBean implements Serializable {
    @PersistenceContext
    private EntityManager em;

    private Long id;
    private BookEntity book;

    public void openBook(){
        System.out.println("Book" + id);
        book = em.find(BookEntity.class, id);
    }

    public BookEntity getBook(){
        return book;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
