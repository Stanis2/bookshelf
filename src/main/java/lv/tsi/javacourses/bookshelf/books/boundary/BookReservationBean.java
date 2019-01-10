package lv.tsi.javacourses.bookshelf.books.boundary;

import lv.tsi.javacourses.bookshelf.auth.boundary.CurrentUser;
import lv.tsi.javacourses.bookshelf.books.model.BookEntity;
import lv.tsi.javacourses.bookshelf.books.model.ReservationEntity;
import lv.tsi.javacourses.bookshelf.books.model.ReservationStatus;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
@Named
public class BookReservationBean {
    @PersistenceContext
    private EntityManager em;

    @Inject
    private CurrentUser currentUser;

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

    public List<ReservationEntity> getReservations() {
        return em.createQuery("select r from Reservation r", ReservationEntity.class).getResultList();
    }
}
