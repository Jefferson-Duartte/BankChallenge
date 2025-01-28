package br.com.compass.service;

import br.com.compass.dao.CustomerDAO;
import br.com.compass.model.BankAccount;
import br.com.compass.model.Customer;
import br.com.compass.util.PasswordUtil;

import java.util.List;


public class CustomerService {

    private CustomerDAO customerDAO = new CustomerDAO();

    public void createCustomer(Customer customer, BankAccount account) {
        customer.setAccounts(account);
        customer.setPassword(PasswordUtil.hashPassword(customer.getPassword()));
        customerDAO.createCustomer(customer);
    }

    public Customer login(String cpf, String password) {

        Customer customer = customerDAO.findByCpf(cpf);

        if (customer == null) {
            System.out.println("Customer with CPF " + cpf + " does not exist!");
            return null;
        }

        if (!PasswordUtil.checkPassword(password, customer.getPassword())) {
            System.out.println("Invalid Password!");
            return null;
        }

        return customer;
    }

    public List<BankAccount> loadCustomerAccounts(Customer customer){
        return customerDAO.loadCustomerAccounts(customer);
    }
}
