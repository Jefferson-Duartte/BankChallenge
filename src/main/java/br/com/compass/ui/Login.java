package br.com.compass.ui;

import br.com.compass.model.BankAccount;
import br.com.compass.model.Customer;
import br.com.compass.service.CustomerService;

import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Login {

    private static CustomerService customerService = new CustomerService();

    public static void login(Scanner scanner) {

        try {
            System.out.print("Enter your cpf: ");
            String inputCpf = scanner.nextLine();
            System.out.print("Enter your password: ");
            String inputPassword = scanner.nextLine();

            Customer loggedCustomer = customerService.login(inputCpf, inputPassword);
            System.out.println("Welcome, " + loggedCustomer.getName() + "!");

            BankAccount selectedAccount = selectAccountForManagement(loggedCustomer, scanner);


            if (selectedAccount != null) {
                BankMenu.show(scanner, selectedAccount);
            }

        } catch (NullPointerException e) {
            MainMenu.run();
        }

    }

    private static BankAccount selectAccountForManagement(Customer customer, Scanner scanner) {
        List<BankAccount> accounts = customerService.loadCustomerAccounts(customer);
        accounts = accounts.stream().sorted(Comparator.comparing(BankAccount::getAccountNumber)).toList();

        if (!accounts.isEmpty()) {

            if (accounts.size() == 1) {
                return accounts.get(0);
            }

            System.out.println("Please select the account you want to manage");
            for (int i = 0; i < accounts.size(); i++) {
                System.out.println(i + 1 + " - " + accounts.get(i));
            }

            System.out.print("Choose an option: ");
            try {
                int accountOption = Integer.parseInt(scanner.nextLine()) - 1;
                return accounts.get(accountOption);

            } catch (InputMismatchException | NumberFormatException | IndexOutOfBoundsException e) {
                System.out.println("Invalid input! Please enter a number.");
                return selectAccountForManagement(customer, scanner);
            }

        }

        return null;
    }

}
