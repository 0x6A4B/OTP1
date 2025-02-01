package dev._x6a4b.otp1.dao;

import dev._x6a4b.otp1.datasource.MariaDbJpaConnection;
import dev._x6a4b.otp1.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

public class UserDao {

    public void persist(User user){
        EntityManager em = MariaDbJpaConnection.getInstance();
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
    }

    public User find(long id){
        EntityManager em = MariaDbJpaConnection.getInstance();
        return em.find(User.class, id);
    }

    public User findByName(String userName){
        EntityManager em = MariaDbJpaConnection.getInstance();
        User user = null;
        try {
            user = em.createQuery(
                "FROM User WHERE userName = '" + userName + "'", User.class)
                .getSingleResult();
        }catch (NoResultException e){
            System.out.println("No result for query: findByName " + userName);
        }
        return user;
    }

    public void update(User user){
        EntityManager em = MariaDbJpaConnection.getInstance();
        em.getTransaction().begin();
        em.merge(user);
        em.getTransaction().commit();
    }

    public void delete(User user){
        EntityManager em = MariaDbJpaConnection.getInstance();
        em.getTransaction().begin();
        em.remove(user);
        em.getTransaction().commit();
    }
}
