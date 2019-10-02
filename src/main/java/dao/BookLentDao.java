package dao;

import model.Book;
import model.BookLent;
import model.Client;
import org.hibernate.Session;
import util.HibernateUtil;

import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.List;

public class BookLentDao {

    public List<Book> getBooksLentByClient(Long clientId) {
        // select * from BookLent bl join Book b on bl.book_id = b.id where bl.client_id = ?
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Book> criteriaQuery = cb.createQuery(Book.class);
            Root<BookLent> blRoot = criteriaQuery.from(BookLent.class);
            Join<BookLent, Book> joinBook = blRoot.join("book", JoinType.LEFT);
            Join<BookLent, Book> joinClient = blRoot.join("client", JoinType.LEFT);
            criteriaQuery.select(cb.construct(Book.class,
                    joinBook.get("id"),
                    joinBook.get("title"),
                    joinBook.get("yearWritten"),
                    joinBook.get("numberOfPages"),
                    joinBook.get("numberOfAvailableCopies")
            )).where(
                    cb.equal(joinClient.get("id"), clientId)
            );
            return session.createQuery(criteriaQuery).list();
        }
    }

    public List<Book> getBooksUnreturnedByClient(Client client) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Book> criteriaQuery = cb.createQuery(Book.class);
            Root<BookLent> blRoot = criteriaQuery.from(BookLent.class);
            Join<BookLent, Book> joinBook = blRoot.join("book", JoinType.LEFT);
            Join<BookLent, Book> joinClient = blRoot.join("client", JoinType.LEFT);
            criteriaQuery.select(cb.construct(Book.class,
                    joinBook.get("id"),
                    joinBook.get("title"),
                    joinBook.get("yearWritten"),
                    joinBook.get("numberOfPages"),
                    joinBook.get("numberOfAvailableCopies")
            )).where(cb.and(
                    cb.equal(joinClient.get("id"), client.getId()),
                    cb.isNull(blRoot.get("dateReturned"))
            ));
            return session.createQuery(criteriaQuery).list();
        }
    }

    public List<Book> getBooksUnreturned() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Book> criteriaQuery = cb.createQuery(Book.class);
            Root<BookLent> blRoot = criteriaQuery.from(BookLent.class);
            Join<BookLent, Book> joinBook = blRoot.join("book", JoinType.LEFT);
            criteriaQuery.select(cb.construct(Book.class,
                    joinBook.get("id"),
                    joinBook.get("title"),
                    joinBook.get("yearWritten"),
                    joinBook.get("numberOfPages"),
                    joinBook.get("numberOfAvailableCopies")
            )).where(cb.isNull(blRoot.get("dateReturned")));
            return session.createQuery(criteriaQuery).list();
        }
    }

    public List<Book> getBooksFromLastNDays(Long lastNDays) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Book> criteriaQuery = cb.createQuery(Book.class);
            Root<BookLent> blRoot = criteriaQuery.from(BookLent.class);
            Join<BookLent, Book> joinBook = blRoot.join("book", JoinType.LEFT);
            criteriaQuery.select(cb.construct(Book.class,
                    joinBook.get("id"),
                    joinBook.get("title"),
                    joinBook.get("yearWritten"),
                    joinBook.get("numberOfPages"),
                    joinBook.get("numberOfAvailableCopies")
            )).where(cb.greaterThan(blRoot.get("dateReturned"), LocalDate.now().minusDays(lastNDays)));
            return session.createQuery(criteriaQuery).list();
        }
    }

    public List<Book> getMostBorrowedBooks() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Book> criteriaQuery = cb.createQuery(Book.class);
            Root<BookLent> blRoot = criteriaQuery.from(BookLent.class);
            criteriaQuery.select(blRoot.get("book")).groupBy(blRoot.get("book")).orderBy(cb.desc(cb.count(blRoot.get("book"))));
            return session.createQuery(criteriaQuery).list();
        }
    }

    public List<Client> getMostActiveUsers() {
        /*SELECT *, count(hibernate_library.booklent.client_id) FROM `client`
        JOIN hibernate_library.booklent on hibernate_library.booklent.client_id = hibernate_library.`client`.id
        GROUP BY hibernate_library.`client`.id ORDER BY count(hibernate_library.booklent.client_id) DESC;*/
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Client> criteriaQuery = cb.createQuery(Client.class);
            Root<BookLent> blRoot = criteriaQuery.from(BookLent.class);
            criteriaQuery.select(blRoot.get("client")).groupBy(blRoot.get("client")).orderBy(cb.desc(cb.count(blRoot.get("client"))));
            return session.createQuery(criteriaQuery).list();
        }
    }
}

