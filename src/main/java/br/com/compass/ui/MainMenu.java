package br.com.compass.ui;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MainMenu {

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

    private static void mainMenu(Scanner scanner) {
        boolean running = true;

        while (running) {
            System.out.println("========= Main Menu =========");
            System.out.println("|| 1. Login                ||");
            System.out.println("|| 2. Account Opening      ||");
            System.out.println("|| 0. Exit                 ||");
            System.out.println("=============================");
            System.out.print("Choose an option: ");

            try {
                int option = Integer.parseInt(scanner.nextLine());

                switch (option) {
                    case 1:
                        if (Login.login(scanner)) {
                            BankMenu.show(scanner);
                        } else {
                            continue;
                        }
                    case 2:
                        AccountOpeningMenu.show(scanner);
                        break;
                    case 0:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid option! Please try again.");
                }
            } catch (InputMismatchException | NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        }
    }

}