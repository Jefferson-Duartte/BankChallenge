package br.com.compass.dao;

import br.com.compass.model.BankAccount;
import br.com.compass.util.JpaUtil;
import jakarta.persistence.EntityManager;

public class AccountDAO {

    public AccountDAO() {
    }

    public BankAccount findById(Long id) {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            return em.find(BankAccount.class, id);
        } finally {
            em.close();
        }
    }

    public void update(BankAccount account) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(account);
            em.getTransaction().commit();

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }

    }

    public BankAccount findAccountByNumber(String accountNumber) {
        EntityManager em = JpaUtil.getEntityManager();
        BankAccount account;
        try {
            account = em.createQuery("SELECT a FROM BankAccount a WHERE a.accountNumber = :accountNumber", BankAccount.class)
                    .setParameter("accountNumber", accountNumber).getSingleResult();

        } finally {
            em.close();
        }
        return account;
    }
}
