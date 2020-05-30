package lt.vu.mif.jate.tasks.task03.jpa;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class DbManager implements AutoCloseable {

    private final EntityManager em;

    public DbManager() {
        this.em = Persistence.createEntityManagerFactory("stud").createEntityManager();
    }

    public <T> List<T> getListOf(Class<T> c) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(c);
        Root<T> root = cq.from(c);
        cq.select(root);
        TypedQuery<T> q = em.createQuery(cq);
        return q.getResultList();
    }

    public <T> List<T> getListOf(Class<T> c, String attribute) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(c);
        Root<T> root = cq.from(c);
        cq.select(root);
        cq.orderBy(cb.asc(root.get(attribute)));
        TypedQuery<T> q = em.createQuery(cq);
        return q.getResultList();
    }

    public <T> T getById(Class<T> c, int id) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(c);
        Root<T> root = cq.from(c);
        cq.where(cb.equal(root.get("id"), id));
        TypedQuery<T> q = em.createQuery(cq);
        return q.getSingleResult();
    }

    @Override
    public void close() {
    }
}
