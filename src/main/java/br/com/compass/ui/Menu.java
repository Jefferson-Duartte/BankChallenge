package br.com.compass.ui;

import br.com.compass.model.BankAccount;
import br.com.compass.model.Customer;
import br.com.compass.model.enums.AccountType;
import br.com.compass.service.AccountService;
import br.com.compass.service.CustomerService;

import java.util.Scanner;

public class Menu {

    private static CustomerService customerService = new CustomerService();

    private static AccountService accountService = new AccountService();

    static Scanner scanner;

    public static void run() {
        scanner = new Scanner(System.in);
        try {
            mainMenu(scanner);

        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }


    private static void accountOpening(Scanner scanner) {

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

            if (confirmedOperation(scanner)) {
                customerService.createCustomer(customer, account);
                System.out.println("\nAccount created successfully!");
                confirmed = true;
            } else {
                System.out.println("\nLet's edit the details.\n");
            }
        }
        mainMenu(scanner);
    }

    private static boolean confirmedOperation(Scanner scanner) {
        while (true) {
            System.out.print("Are the details correct? (yes/no): ");
            String confirmed = scanner.nextLine().trim().toLowerCase();

            if (confirmed.equals("yes") || confirmed.equals("y")) {
                return true;
            } else if (confirmed.equals("no") || confirmed.equals("n")) {
                return false;
            } else {
                System.out.println("Invalid input! Please type 'yes' or 'no'.");
            }
        }
    }


    private static BankAccount chooseAccountType(Scanner scanner) {

        System.out.println("Account type");

        System.out.println("1 - Conta Poupança");
        System.out.println("2 - Conta Corrente");
        System.out.println("3 - Conta Salário");

        System.out.print("Chose an option: ");
        int accountTypeOption = Integer.parseInt(scanner.nextLine());

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

            int option = Integer.parseInt(scanner.nextLine());

            switch (option) {
                case 1:
                    if (login(scanner)) {
                        bankMenu(scanner);
                    } else {
                        continue;
                    }
                    return;
                case 2:
                    accountOpening(scanner);
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }

    private static boolean login(Scanner scanner) {
        System.out.print("Enter your cpf: ");
        String inputCpf = scanner.nextLine();
        System.out.print("Enter your password: ");
        String inputPassword = scanner.nextLine();

        try {
            Customer loggedCustomer = customerService.login(inputCpf, inputPassword);
            System.out.println("Welcome, " + loggedCustomer.getName() + "!");
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println("Login failed: " + e.getMessage());
        }
        return false;
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

            int option = Integer.parseInt(scanner.nextLine());
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