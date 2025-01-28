package br.com.compass.service;

import br.com.compass.dao.AccountDAO;
import br.com.compass.dao.CustomerDAO;
import br.com.compass.model.BankAccount;
import br.com.compass.model.Customer;
import br.com.compass.model.enums.AccountType;

import java.math.BigDecimal;

public class AccountService {

    private AccountDAO accountDAO = new AccountDAO();
    private CustomerDAO customerDAO = new CustomerDAO();

    public BankAccount createAccount(Customer customer, AccountType accountType) {

        BankAccount newAccount = new BankAccount();
        newAccount.setAccountType(accountType);
        newAccount.setCustomer(customer);

        customer.getAccounts().add(newAccount);

        customerDAO.update(customer);

        return newAccount;

    }

    public void deposit(BankAccount account, BigDecimal amount) {

        BankAccount existingAccount = accountDAO.findById(account.getId());

        if (existingAccount == null) {
            System.out.println("Account not found");
            return;
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("The value must be greater than zero");
            return;
        }

        existingAccount.setBalance(account.getBalance().add(amount));
        accountDAO.update(existingAccount);
        System.out.println("Successful deposit");
    }

    public void withdraw(BankAccount account, BigDecimal amount) {

        BankAccount existingAccount = accountDAO.findById(account.getId());

        if (existingAccount == null) {
            System.out.println("Account not found");
            return;
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("The value must be greater than zero");
            return;
        }

        if (existingAccount.getBalance().compareTo(amount) < 0) {
            System.out.println("Insufficient balance for withdrawal");
            return;
        }

        existingAccount.setBalance(existingAccount.getBalance().subtract(amount));
        accountDAO.update(existingAccount);
        System.out.println("Successful withdraw");

    }

    public BigDecimal checkBalance(BankAccount account) {
        BankAccount existingAccount = accountDAO.findById(account.getId());
        return existingAccount.getBalance();
    }


}
