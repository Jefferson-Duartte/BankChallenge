package br.com.compass.service;

import br.com.compass.dao.AccountDAO;
import br.com.compass.model.BankAccount;

import java.math.BigDecimal;

public class AccountService {

    private AccountDAO accountDAO = new AccountDAO();

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

}
