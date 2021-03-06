package lv.tsi.javacourses.bookshelf.books.boundary;

import lv.tsi.javacourses.bookshelf.auth.boundary.CurrentUser;
import lv.tsi.javacourses.bookshelf.books.model.ReservationEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Named
@ViewScoped
public class MyBooksBean implements Serializable {
    private static Logger logger = LoggerFactory.getLogger(MyBooksBean.class);
    @PersistenceContext
    private EntityManager em;
    @Inject
    private CurrentUser currentUser;

    private List<ReservationEntity> availableResult;
    private List<ReservationEntity> inQueueResult;
    private List<ReservationEntity> takenBooksResult;
    private List<ReservationEntity> closedBooksResult;

    public void prepare() {
        logger.debug("Preparing ** {} ** books.", currentUser.getUser().getLoginName());
        availableResult = new ArrayList<>();
        inQueueResult = new ArrayList<>();
        takenBooksResult = new ArrayList<>();
        closedBooksResult = new ArrayList<>();
        List<ReservationEntity> userReservations = em.createQuery("select r from Reservation r where r.user = :user and r.status = 'ACTIVE'", ReservationEntity.class)
                .setParameter("user", currentUser.getUser()).getResultList();

        logger.debug("User reservations {} active", userReservations.size());

        for (ReservationEntity r : userReservations) {
            Long reservationId = r.getId();
            logger.trace("Checking reservation {}", r);
            Optional<ReservationEntity> firstReservation = em.createQuery("select r from Reservation r where r.book = :book and r.status <> 'CLOSED' " +
                    "order by r.created", ReservationEntity.class)
                    .setParameter("book", r.getBook()).getResultStream().findFirst();
            if (firstReservation.isEmpty() || firstReservation.get().getId().equals(reservationId)) {
                availableResult.add(r);
            } else {
                inQueueResult.add(r);
            }
        }

        takenBooksResult = em.createQuery("select r from Reservation r where r.user = :user and r.status = 'TAKEN'", ReservationEntity.class)
                .setParameter("user", currentUser.getUser()).getResultList();

        closedBooksResult = em.createQuery("select distinct r from Reservation r where r.user = :user and r.status = 'CLOSED'", ReservationEntity.class)
                .setParameter("user", currentUser.getUser()).getResultList();
    }

    public List<ReservationEntity> getAvailableBooks () {
        return availableResult;
    }

    public List<ReservationEntity> getInQueueBooks () {
        return inQueueResult;
    }

    public List<ReservationEntity> getTakenBooks () {
        return takenBooksResult;
    }

    public List<ReservationEntity> getClosedBooksResult() {
        return closedBooksResult;
    }
}
