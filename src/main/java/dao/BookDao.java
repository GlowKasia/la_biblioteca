package dao;

import model.Book;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.HibernateUtil;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookDao {

    public List<Book> getAvailableCopies(String command) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();

            CriteriaQuery<Book> criteriaQuery = cb.createQuery(Book.class);

            Root<Book> rootTable = criteriaQuery.from(Book.class);

            if (command.equalsIgnoreCase("lk")) {
                criteriaQuery.select(rootTable).where(cb.greaterThan(rootTable.get("numberOfAvailableCopies"), 0));
            } else if (command.equalsIgnoreCase("lnk")){
                criteriaQuery.select(rootTable).where(cb.equal(rootTable.get("numberOfAvailableCopies"), 0));
            }
            return session.createQuery(criteriaQuery).list();
        }
    }
}

