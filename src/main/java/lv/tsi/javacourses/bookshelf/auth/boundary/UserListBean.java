package lv.tsi.javacourses.bookshelf.auth.boundary;

import lv.tsi.javacourses.bookshelf.auth.model.UserEntity;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class UserListBean implements Serializable {
    @PersistenceContext
    private EntityManager em;

    private String name;

    public List<UserEntity> getUsers() {
        if (name == null) {
            return em.createQuery("select u from User u", UserEntity.class).getResultList();
        } else {
            return em.createQuery("select u from User u where lower(u.loginName) like :name", UserEntity.class)
                    .setParameter("name", "%" + name.toLowerCase() + "%")
                    .getResultList();
        }
    }

    public void doSearch() {
        System.out.println("Searching for: " + name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
