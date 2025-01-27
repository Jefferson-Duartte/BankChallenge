package br.com.compass.service;

import br.com.compass.dao.CustomerDAO;
import br.com.compass.model.BankAccount;
import br.com.compass.model.Customer;
import br.com.compass.util.PasswordUtil;

import java.util.List;


public class CustomerService {

    private CustomerDAO customerDAO = new CustomerDAO();

    public void createCustomer(Customer customer, BankAccount account) {
        customerDAO.createCustomer(customer, account);
    }

    public Customer login(String cpf, String password) {

        Customer customer = customerDAO.findByCpf(cpf);

        if (customer == null) {
            throw new IllegalArgumentException("Customer with CPF " + cpf + " does not exist!");
        }

        if (!PasswordUtil.checkPassword(password, customer.getPassword())) {
            throw new IllegalArgumentException("Invalid Password!");
        }

        return customer;
    }

    public List<BankAccount> loadCustomerAccounts(Customer customer){
        return customerDAO.loadCustomerAccounts(customer);
    }
}
