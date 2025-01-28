package br.com.compass.ui;

import br.com.compass.model.BankAccount;
import br.com.compass.service.AccountService;
import br.com.compass.util.ValidationUtil;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BankMenu {

    private static AccountService accountService = new AccountService();


    public static void show(Scanner scanner, BankAccount account) {
        boolean running = true;

        while (running) {
            System.out.println("========= Bank Menu =========");
            System.out.println("========= Account selected: " + account + " =========");
            System.out.println("|| 1. Deposit              ||");
            System.out.println("|| 2. Withdraw             ||");
            System.out.println("|| 3. Check Balance        ||");
            System.out.println("|| 4. Transfer             ||");
            System.out.println("|| 5. Bank Statement       ||");
            System.out.println("|| 6. Open New Account     ||");
            System.out.println("|| 0. Exit                 ||");
            System.out.println("=============================");
            System.out.print("Choose an option: ");

            int option = Integer.parseInt(scanner.nextLine());
            switch (option) {
                case 1:
                    deposit(account, scanner);
                    break;
                case 2:
                    // ToDo...
                    withdraw(account, scanner);
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
                case 6:
                    // ToDo...
                    System.out.println("Open new account.");
                    break;
                case 0:
                    // ToDo...
                    System.out.println("Exiting...");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }

    private static void deposit(BankAccount account, Scanner scanner) {

        BigDecimal amount = validateAmount(scanner);

        System.out.println("Do you confirm the deposit of R$" + amount.toString().replace(".", ",") + " in your account " + account);

        if (ValidationUtil.confirmedOperation(scanner)) {
            accountService.deposit(account, amount);
        } else {
            System.out.println("Operation cancelled");
            BankMenu.show(scanner, account);
        }
    }

    private static void withdraw(BankAccount account, Scanner scanner) {
        BigDecimal amount = validateAmount(scanner);

        System.out.println("Do you confirm the withdraw of " + amount + " in your account " + account);

        if (ValidationUtil.confirmedOperation(scanner)) {
            accountService.withdraw(account, amount);

        } else {
            System.out.println("Operation cancelled");
            BankMenu.show(scanner, account);
        }
    }


    private static BigDecimal validateAmount(Scanner scanner) {

        BigDecimal amount = null;

        while (amount == null) {

            System.out.print("Enter the amount: ");

            try {
                amount = BigDecimal.valueOf(Double.parseDouble(scanner.nextLine().replace(",", ".")));
                if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                    System.out.println("The value must be greater than zero. Please try again.");
                    amount = null;
                }
            } catch (InputMismatchException | NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }

        }

        return amount;

    }

}
