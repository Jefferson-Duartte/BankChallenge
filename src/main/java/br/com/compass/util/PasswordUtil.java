package br.com.compass.util;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class PasswordUtil {

    public static String hashPassword(String password){
        char[] passwordChars = password.toCharArray();
        return BCrypt.withDefaults().hashToString(12, passwordChars);
    }

    public static boolean checkPassword(String password, String encodedPassword){
        return BCrypt.verifyer().verify(password.toCharArray(), encodedPassword.toCharArray()).verified;
    }

}
