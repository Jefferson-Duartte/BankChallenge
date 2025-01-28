package br.com.compass.ui;

import br.com.compass.model.BankAccount;
import br.com.compass.model.Customer;
import br.com.compass.model.enums.AccountType;
import br.com.compass.service.CustomerService;
import br.com.compass.util.ValidationUtil;

import java.util.InputMismatchException;
import java.util.Scanner;

public class AccountOpeningMenu {

    private static CustomerService customerService = new CustomerService();

    public static void show(Scanner scanner) {
        boolean confirmed = false;

        Customer customer;
        BankAccount account;

        while (!confirmed) {

            System.out.println("Please, fill in the fields below");

            System.out.println();

            System.out.print("Full Name: ");
            String fullName = scanner.nextLine();

            System.out.print("Birth date(dd/MM/yyyy): ");
            String birthDate = scanner.nextLine();

            System.out.print("CPF: ");
            String cpf = scanner.nextLine();

            System.out.print("Phone Number: ");
            String phoneNumber = scanner.nextLine();

            System.out.print("Password: ");
            String password = scanner.nextLine();

            account = chooseAccountType(scanner);

            customer = new Customer(fullName, birthDate, cpf, password, phoneNumber);

            if (ValidationUtil.isPasswordValid(customer)) {
                if (ValidationUtil.confirmedOperation(scanner)) {
                    customerService.createCustomer(customer, account);
                    System.out.println("\nAccount created successfully!");
                    confirmed = true;
                } else {
                    System.out.println("\nLet's edit the details.\n");
                }
            }
        }
    }


    private static BankAccount chooseAccountType(Scanner scanner) {

        boolean confirmed = false;

        BankAccount account = null;

        while (!confirmed) {

            System.out.println("Account type");

            System.out.println("1 - Conta Poupança");
            System.out.println("2 - Conta Corrente");
            System.out.println("3 - Conta Salário");

            System.out.print("Chose an option: ");

            account = new BankAccount();

            try {
                int accountTypeOption = Integer.parseInt(scanner.nextLine());

                switch (accountTypeOption) {
                    case 1:
                        account.setAccountType(AccountType.CONTA_POUPANCA);
                        confirmed = true;
                        break;
                    case 2:
                        account.setAccountType(AccountType.CONTA_CORRENTE);
                        confirmed = true;
                        break;
                    case 3:
                        account.setAccountType(AccountType.CONTA_SALARIO);
                        confirmed = true;
                        break;
                    default:
                        System.out.println("Invalid option! Please try again.");
                }

            } catch (InputMismatchException | NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        }

        return account;
    }

}
