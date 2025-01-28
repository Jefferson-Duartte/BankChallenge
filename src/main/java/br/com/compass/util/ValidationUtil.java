package br.com.compass.util;

import br.com.compass.model.Customer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class ValidationUtil {

    public static boolean isPasswordValid(String customerName, String customerPassword, String customerCpf, String customerBirthDate) {

        if (customerPassword.equals(customerCpf)) {
            System.out.println("The password cannot be the same as your CPF");
            return false;
        }
        if (customerPassword.equals(customerBirthDate)) {
            System.out.println("The password cannot be the same as your Birth Date");
            return false;
        }

        if (customerName.contains(customerPassword) || customerPassword.equalsIgnoreCase(customerName)) {
            System.out.println("The password cannot be the same as your Name");
            return false;
        }

        return true;
    }

    public static boolean confirmedOperation(Scanner scanner) {
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

    public static boolean isCpfValid(String customerCpf) {

        if (customerCpf.length() != 11 || !customerCpf.matches("\\d{11}")) {
            System.out.println("The CPF is invalid!");
            return false;
        }

        return true;
    }

    public static boolean isBirthDateValid(String birthDate) {

        try {
            LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            return true;
        } catch (DateTimeParseException e) {
            System.out.println("Birth date is invalid!");
            return false;
        }

    }

    public static boolean isPhoneNumberValid(String phoneNumber) {
        if (phoneNumber.matches("\\d{1,13}")) {
            return true;
        } else {
            System.out.println("Phone number is invalid!");
            return false;
        }
    }
}
