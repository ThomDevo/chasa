package com.example.chasa.utilities;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
public class EMF {
    private static EntityManagerFactory emfInstance =
            Persistence.createEntityManagerFactory("chasa");

    private EMF() {
    }

    public static EntityManagerFactory getEMF() {
        return emfInstance;
    }

    public static EntityManager getEM() {
        return emfInstance.createEntityManager();
    }

    /*	Create EntityManager in others classes
     * EntityManager em = EMF.getEM();
     * try {
     *     // ... do stuff with em ...
     * } finally {
     *     em.close();
     * }
     */
}
