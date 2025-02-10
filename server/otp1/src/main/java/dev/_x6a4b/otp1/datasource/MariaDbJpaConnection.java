package dev._x6a4b.otp1.datasource;

import jakarta.persistence.*;

public class MariaDbJpaConnection {
    private static EntityManagerFactory emf = null;
    private static EntityManager em = null;

    public static EntityManager getInstance() {
        // you need to add synchronization if you run in a multi-threaded environment

        if (em==null) {
            if (emf==null) {
                emf = Persistence.createEntityManagerFactory("I_SPY_U");
            }
            em = emf.createEntityManager();
        }
        return em;
    }

    // how to have a test database so real one won't be affected from tests?
    public static EntityManager getTestInstance() {
        if (em==null) {
            if (emf==null) {
                emf = Persistence.createEntityManagerFactory("I_SPY_U_TEST");
            }
            em = emf.createEntityManager();
        }
        return em;
    }
}
