package lv.tsi.javacourses.bookshelf.books.boundary;

import lv.tsi.javacourses.bookshelf.auth.boundary.CurrentUser;
import lv.tsi.javacourses.bookshelf.books.model.BookEntity;
import lv.tsi.javacourses.bookshelf.books.model.ReservationEntity;
import lv.tsi.javacourses.bookshelf.books.model.ReservationStatus;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.Serializable;

@ViewScoped
@Named
public class BookBean implements Serializable {
    @PersistenceContext
    private EntityManager em;

    @Inject
    private CurrentUser currentUser;

    private Long id;
    private BookEntity book;

    public void openBook(){
        System.out.println("Book" + id);
        book = em.find(BookEntity.class, id);
    }

    @Transactional
    public void reserve(Long id) {
        System.out.println("Reserving a book " + id + " for user " + currentUser.getUser().getId()
                + " named " + currentUser.getUser().getLoginName());

        BookEntity book = em.find(BookEntity.class, id);
        ReservationEntity reservation = new ReservationEntity();
        reservation.setBook(book);
        reservation.setUser(currentUser.getUser());
        reservation.setStatus(ReservationStatus.ACTIVE);
        em.persist(reservation);
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
