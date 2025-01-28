package br.com.compass.dao;

import br.com.compass.model.Transaction;
import br.com.compass.util.JpaUtil;
import jakarta.persistence.EntityManager;

public class TransactionDAO {

    public void save(Transaction transaction) {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(transaction);

            em.getTransaction().commit();

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.out.println("Error: " + e.getMessage());
        } finally {
            JpaUtil.closeEntityManager(em);
        }
    }
}
