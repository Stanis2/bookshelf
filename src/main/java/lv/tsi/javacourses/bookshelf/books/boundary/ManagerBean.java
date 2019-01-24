package lv.tsi.javacourses.bookshelf.books.boundary;

import lv.tsi.javacourses.bookshelf.books.model.ReservationEntity;
import lv.tsi.javacourses.bookshelf.books.model.ReservationStatus;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Named
@ViewScoped
public class ManagerBean implements Serializable {
    @PersistenceContext
    private EntityManager em;

    private List<ReservationEntity> bookReservation;
    private List<ReservationEntity> takenBooksResult;
    private List<ReservationEntity> closedBooksResult;

    public void reservations() {
        bookReservation = new ArrayList<>();
        takenBooksResult = new ArrayList<>();
        closedBooksResult = new ArrayList<>();

        List<ReservationEntity> userReservations = em.createQuery("select r from Reservation r where r.status = 'ACTIVE'", ReservationEntity.class).getResultList();

        for (ReservationEntity r : userReservations) {
            Long reservationId = r.getId();
            Optional<ReservationEntity> firstReservation = em.createQuery("select r from Reservation r where r.book = :book and r.status <> 'CLOSED' " +
                    "order by r.created", ReservationEntity.class)
                    .setParameter("book", r.getBook()).getResultStream().findFirst();
            if (firstReservation.isEmpty() || firstReservation.get().getId().equals(reservationId)) {
                bookReservation.add(r);
            }
        }
        takenBooksResult = em.createQuery("select r from Reservation r where r.status = 'TAKEN'", ReservationEntity.class).getResultList();
        closedBooksResult = em.createQuery("select r from Reservation r where r.status = 'CLOSED'", ReservationEntity.class).getResultList();
    }

    @Transactional
    public void giveBook (ReservationEntity reservation) {
        ReservationEntity r = em.merge(reservation);
        r.setStatus(ReservationStatus.TAKEN);
        reservations();
    }

    @Transactional
    public void takeBook (ReservationEntity reservation) {
        ReservationEntity r = em.merge(reservation);
        r.setStatus(ReservationStatus.CLOSED);
        reservations();
    }

    public List<ReservationEntity> getBookReservation () {
        return bookReservation;
    }
    public List<ReservationEntity> getTakenBooks () {
        return takenBooksResult;
    }
    public List<ReservationEntity> getClosedBooks () {
        return closedBooksResult;
    }
}