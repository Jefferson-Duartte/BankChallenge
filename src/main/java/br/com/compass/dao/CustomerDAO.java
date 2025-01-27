package br.com.compass.dao;

import br.com.compass.model.BankAccount;
import br.com.compass.model.Customer;
import br.com.compass.util.JpaUtil;
import br.com.compass.util.PasswordUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.util.List;


public class CustomerDAO {

    public void createCustomer(Customer customer, BankAccount account) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            customer.setAccounts(account);
            customer.setPassword(PasswordUtil.hashPassword(customer.getPassword()));
            em.persist(customer);

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

    public Customer findByCpf(String cpf) {
        EntityManager em = JpaUtil.getEntityManager();
        Customer customer;
        try {
            customer = em.createQuery("SELECT c FROM Customer c WHERE c.cpf = :cpf", Customer.class)
                    .setParameter("cpf", cpf)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
        return customer;
    }

    public List<BankAccount> loadCustomerAccounts(Customer customer) {
        EntityManager em = JpaUtil.getEntityManager();
        List<BankAccount> accounts = null;

        try {
            accounts = em.createQuery("SELECT a from BankAccount a WHERE a.customer = :customer", BankAccount.class)
                    .setParameter("customer", customer)
                    .getResultList();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            JpaUtil.closeEntityManager(em);
        }

        return accounts;
    }

}