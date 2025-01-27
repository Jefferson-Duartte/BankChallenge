package br.com.compass.ui;

import br.com.compass.model.Customer;
import br.com.compass.service.CustomerService;

import java.util.Scanner;

public class Login {

    private static CustomerService customerService = new CustomerService();

    public static boolean login(Scanner scanner) {
        System.out.print("Enter your cpf: ");
        String inputCpf = scanner.nextLine();
        System.out.print("Enter your password: ");
        String inputPassword = scanner.nextLine();

        try {
            Customer loggedCustomer = customerService.login(inputCpf, inputPassword);
            System.out.println("Welcome, " + loggedCustomer.getName() + "!");

            System.out.println(customerService.loadCustomerAccounts(loggedCustomer));


            return true;
        } catch (IllegalArgumentException e) {
            System.out.println("Login failed: " + e.getMessage());
        }
        return false;
    }
}
