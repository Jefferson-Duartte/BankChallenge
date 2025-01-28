package br.com.compass.dao;

import br.com.compass.model.BankAccount;
import br.com.compass.util.JpaUtil;
import jakarta.persistence.EntityManager;

import java.math.BigDecimal;

public class AccountDAO {

    public AccountDAO() {
    }

    public BankAccount deposit(BankAccount account, BigDecimal amount) {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            BankAccount existingAccount = em.find(BankAccount.class, account.getId());

            if (existingAccount != null) {
                existingAccount.setBalance(existingAccount.getBalance().add(amount));
                em.merge(existingAccount);
            }

            em.getTransaction().commit();
            return existingAccount;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }

        return null;
    }

}
