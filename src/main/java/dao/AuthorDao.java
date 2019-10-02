package dao;

import model.Author;
import model.Book;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import util.HibernateUtil;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AuthorDao {


    public List<Author> getByName(String surname) {
        List<Author> list = new ArrayList<>();
        SessionFactory factory = HibernateUtil.getSessionFactory();
        try (Session session = factory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();

            CriteriaQuery<Author> criteriaQuery = cb.createQuery(Author.class);

            Root<Author> rootTable = criteriaQuery.from(Author.class);

            criteriaQuery.select(autorRoot).where(cb.equal(authorRoot.get("suername"), surname));

            authorList.addAll(session.createQuery(criteriaQuery).list());
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return authorList;
    }





    //public void saveOrUpdate(Author entity) {
    //        SessionFactory factory = HibernateUtil.getSessionFactory();
    //        Transaction transaction = null;
    //        try (Session session = factory.openSession()) {
    //            transaction = session.beginTransaction();
    //
    //            session.saveOrUpdate(entity);
    //
    //            transaction.commit();
    //        } catch (HibernateException he) {
    //            if (transaction != null) {
    //                transaction.rollback();
    //            }
    //        }
    //    }
    //
    //    public Optional<Author> getById(Long id) {
    //        SessionFactory factory = HibernateUtil.getSessionFactory();
    //        try (Session session = factory.openSession()) {
    //            Author entity = session.get(Author.class, id);
    //
    //            return Optional.ofNullable(entity);
    //        }
}
