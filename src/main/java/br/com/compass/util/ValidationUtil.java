package br.com.compass.util;

import br.com.compass.model.Customer;

import java.time.format.DateTimeFormatter;

public class ValidationUtil {

    public static boolean isPasswordValid(Customer customer) {

        String customerName = customer.getName();
        String customerPassword = customer.getPassword();
        String customerCpf = customer.getCpf();
        String customerBirthDate = customer.getBirthDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).replace("/", "");
        System.out.println(customerBirthDate);

        if (customerPassword.equals(customerCpf)) {
            System.out.println("The password cannot be the same as your CPF");
            return false;
        }
        if (customerPassword.equals(customerBirthDate) || customerPassword.equals(customer.getBirthDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))) {
            System.out.println("The password cannot be the same as your Birth Date");
            return false;
        }

        if (customerName.contains(customerPassword) || customerPassword.equalsIgnoreCase(customerName)) {
            System.out.println("The password cannot be the same as your Name");
            return false;
        }

        return true;
    }

}
