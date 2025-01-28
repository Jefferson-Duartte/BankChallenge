package br.com.compass.ui;

import br.com.compass.model.BankAccount;
import br.com.compass.model.Customer;
import br.com.compass.model.Transaction;
import br.com.compass.model.enums.AccountType;
import br.com.compass.service.AccountService;
import br.com.compass.service.CustomerService;
import br.com.compass.util.ValidationUtil;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class BankMenu {

    private static AccountService accountService = new AccountService();
    private static CustomerService customerService = new CustomerService();


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
                    withdraw(account, scanner);
                    break;
                case 3:
                    checkBalance(account);
                    break;
                case 4:
                    transfer(scanner, account);
                    break;
                case 5:
                    showBankStatement(account);
                    break;
                case 6:
                    openNewAccount(account, scanner);
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

        System.out.println("Do you confirm the withdraw of R$ " + amount + " in your account " + account);

        if (ValidationUtil.confirmedOperation(scanner)) {
            accountService.withdraw(account, amount);

        } else {
            System.out.println("Operation cancelled");
            BankMenu.show(scanner, account);
        }
    }

    private static void checkBalance(BankAccount account) {
        System.out.println("Your balance: R$: " + accountService.checkBalance(account));
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

    private static void transfer(Scanner scanner, BankAccount senderAccount) {
        System.out.print("Enter the recipient account number: ");
        String recipientAccountNumber = scanner.nextLine();

        BankAccount recipient = accountService.findAccountByNumber(recipientAccountNumber);
        BankAccount sender = accountService.findAccountByNumber(senderAccount.getAccountNumber());

        if (recipient == null) {
            System.out.println("Recipient account not found.");
            return;
        }

        BigDecimal amount = validateAmount(scanner);

        System.out.println("Do you confirm the transfer of R$ " + amount + " to account " + recipient);

        if (ValidationUtil.confirmedOperation(scanner)) {
            accountService.transfer(sender, recipient, amount);
        } else {
            System.out.println("Operation cancelled");
            BankMenu.show(scanner, sender);
        }
    }

    private static void showBankStatement (BankAccount account) {
        if (account.getStatement() == null) {
            System.out.println("No transactions found for this account.");
            return;
        }

        System.out.println("========= Bank Statement =========");
        System.out.println("Account: " + account);

        Set<Transaction> transactions = accountService.findAccountByNumber(account.getAccountNumber()).getStatement().getTransactions();

        transactions = transactions.stream().sorted(Comparator.comparing(Transaction::getDate).reversed()).collect(Collectors.toCollection(LinkedHashSet::new));
        if (transactions.isEmpty()) {
            System.out.println("No transactions available.");
        } else {
            for (Transaction transaction : transactions) {
                System.out.println("Date: " + transaction.getDate() +
                        " | Type: " + transaction.getTransactionType() +
                        " | Amount: R$ " + transaction.getAmount().toString().replace(".", ","));
            }
        }

        System.out.println("=============================");
    }

    private static void openNewAccount(BankAccount account, Scanner scanner) {

        Customer customer = account.getCustomer();

        var existingAccounts = customerService.loadCustomerAccounts(customer);

        List<AccountType> availableAccountTypes = new ArrayList<>();

        for (AccountType type : AccountType.values()) {
            boolean exists = false;
            for (BankAccount acct : existingAccounts) {
                if (acct.getAccountType() == type) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                availableAccountTypes.add(type);
            }
        }

        if (availableAccountTypes.isEmpty()) {
            System.out.println("You already have all account types. No new accounts can be created.");
            return;
        }

        System.out.println("Available account types:");
        for (int i = 0; i < availableAccountTypes.size(); i++) {
            System.out.println(i + 1 + " - " + availableAccountTypes.get(i));
        }

        System.out.print("Choose an option: ");
        int option;
        try {
            option = Integer.parseInt(scanner.nextLine()) - 1;
            if (option < 0 || option >= availableAccountTypes.size()) {
                System.out.println("Invalid option. Operation cancelled.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Operation cancelled.");
            return;
        }

        AccountType selectedType = availableAccountTypes.get(option);
        System.out.println("Do you confirm the creation of a " + selectedType + " account?");

        if (ValidationUtil.confirmedOperation(scanner)) {
            BankAccount newAccount = accountService.createAccount(customer, selectedType);
            System.out.println("Account successfully created: " + newAccount);
        } else {
            System.out.println("Operation cancelled.");
        }
    }

}