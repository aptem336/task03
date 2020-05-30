package lt.vu.mif.jate.tasks.task03.jpa;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class DbManager implements AutoCloseable {

    private EntityManager em;

    public EntityManager getEntityManager() {
        if (em == null) {
            em = Persistence.createEntityManagerFactory("stud").createEntityManager();
        }
        return em;
    }

    public <T> List<T> getListOf(Class<T> c) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(c);
        Root<T> root = criteriaQuery.from(c);
        criteriaQuery.select(root);
        TypedQuery<T> typedQuery = getEntityManager().createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

    public <T> List<T> getListOf(Class<T> c, String attribute) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(c);
        Root<T> root = criteriaQuery.from(c);
        criteriaQuery.select(root);
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get(attribute)));
        TypedQuery<T> typedQuery = getEntityManager().createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

    public <T> T getById(Class<T> c, int id) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(c);
        Root<T> root = criteriaQuery.from(c);
        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), id));
        TypedQuery<T> typedQuery = getEntityManager().createQuery(criteriaQuery);
        return typedQuery.getSingleResult();
    }

    @Override
    public void close() {
    }
}
