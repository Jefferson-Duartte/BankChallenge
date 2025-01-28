package br.com.compass.dao;

import br.com.compass.model.BankStatement;
import br.com.compass.util.JpaUtil;
import jakarta.persistence.EntityManager;

public class BankStatementDAO {
    public void save(BankStatement statement) {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(statement);

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
