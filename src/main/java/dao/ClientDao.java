package dao;

import model.Client;
import model.IBaseEntity;
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
import java.util.Scanner;

public class ClientDao {
    public List<Client> getByName(String surname) {
        List<Client> clientList = new ArrayList<>();
        SessionFactory factory = HibernateUtil.getSessionFactory();
        try (Session session = factory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);
            Root<Client> clientRoot = criteriaQuery.from(Client.class);
            criteriaQuery.select(clientRoot).where(criteriaBuilder.equal(clientRoot.get("surname"), surname));
            clientList.addAll(session.createQuery(criteriaQuery).list());
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return clientList;
    }

    public List<Client> getById(Long id) {
        List<Client> clientList = new ArrayList<>();
        SessionFactory factory = HibernateUtil.getSessionFactory();
        try (Session session = factory.openSession()) {
            Client entity = session.get(Client.class, id);
            Optional<Client> client = Optional.ofNullable(entity);
            if (client.isPresent()) {
                clientList.add(entity);
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return clientList;
    }
}
