package br.com.compass.ui;

import br.com.compass.model.BankAccount;
import br.com.compass.model.Customer;
import br.com.compass.model.enums.AccountType;
import br.com.compass.service.AccountService;
import br.com.compass.service.CustomerService;
import br.com.compass.util.JpaUtil;

import java.util.Scanner;

public class Menu {

    private static CustomerService customerService = new CustomerService();

    private static AccountService accountService = new AccountService();

    static Scanner scanner = new Scanner(System.in);

    public static void run() {
        mainMenu(scanner);
    }

    private static void accountOpening() {

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

        BankAccount account = chooseAccountType();

        Customer customer = new Customer(fullName, birthDate, cpf, password, phoneNumber);
        customerService.createCustomer(customer, account);
    }

    private static BankAccount chooseAccountType() {

        System.out.println("Account type");
        System.out.println("Chose an option");

        System.out.println("1 - Conta Poupança");
        System.out.println("2 - Conta Corrente");
        System.out.println("3 - Conta Salário");

        int accountTypeOption = scanner.nextInt();
        scanner.nextLine();

        BankAccount account = new BankAccount();

        switch (accountTypeOption) {
            case 1:
                account.setAccountType(AccountType.CONTA_POUPANCA);
                break;
            case 2:
                account.setAccountType(AccountType.CONTA_CORRENTE);
                break;
            case 3:
                account.setAccountType(AccountType.CONTA_SALARIO);
                break;
            default:
                System.out.println("Invalid option! Please try again.");

        }

        return account;
    }

    private static void mainMenu(Scanner scanner) {
        boolean running = true;

        while (running) {
            System.out.println("========= Main Menu =========");
            System.out.println("|| 1. Login                ||");
            System.out.println("|| 2. Account Opening      ||");
            System.out.println("|| 0. Exit                 ||");
            System.out.println("=============================");
            System.out.print("Choose an option: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    bankMenu(scanner);
                    return;
                case 2:
                    accountOpening();
                    System.out.println("Account Opening with succes!");
                    break;
                case 0:
                    running = false;
                    JpaUtil.closeEntityManagerFactory();
                    break;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }


    private static void bankMenu(Scanner scanner) {
        boolean running = true;

        while (running) {
            System.out.println("========= Bank Menu =========");
            System.out.println("|| 1. Deposit              ||");
            System.out.println("|| 2. Withdraw             ||");
            System.out.println("|| 3. Check Balance        ||");
            System.out.println("|| 4. Transfer             ||");
            System.out.println("|| 5. Bank Statement       ||");
            System.out.println("|| 0. Exit                 ||");
            System.out.println("=============================");
            System.out.print("Choose an option: ");

            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    // ToDo...
                    System.out.println("Deposit.");
                    break;
                case 2:
                    // ToDo...
                    System.out.println("Withdraw.");
                    break;
                case 3:
                    // ToDo...
                    System.out.println("Check Balance.");
                    break;
                case 4:
                    // ToDo...
                    System.out.println("Transfer.");
                    break;
                case 5:
                    // ToDo...
                    System.out.println("Bank Statement.");
                    break;
                case 0:
                    // ToDo...
                    System.out.println("Exiting...");
                    running = false;
                    return;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }

    }
}