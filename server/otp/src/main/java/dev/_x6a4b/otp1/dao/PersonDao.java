package dev._x6a4b.otp1.dao;

import dev._x6a4b.otp1.datasource.MariaDbJpaConnection;
import dev._x6a4b.otp1.entity.Person;
import jakarta.persistence.EntityManager;

public class PersonDao {

    public void persist(Person person){
        EntityManager em = MariaDbJpaConnection.getInstance();
        em.getTransaction().begin();
        em.persist(person);
        em.getTransaction().commit();
    }

    public Person find(long id){
        EntityManager em = MariaDbJpaConnection.getInstance();
        return em.find(Person.class, id);
    }

    public Person findByUserId(){ return null; }

    public void update(Person person){
        EntityManager em = MariaDbJpaConnection.getInstance();
        em.getTransaction().begin();
        em.merge(person);
        em.getTransaction().commit();
    }

    public void delete(Person person){
        EntityManager em = MariaDbJpaConnection.getInstance();
        em.getTransaction().begin();
        em.remove(person);
        em.getTransaction().commit();
    }
}
