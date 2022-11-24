package ua.java.conferences.utils;

import de.mkammerer.argon2.*;

public final class PasswordHashUtil {

    private static final Argon2 argon2 = Argon2Factory.create();

    private PasswordHashUtil(){}

    public static String encode(String password) {
        return argon2.hash(2,15*1024,1, password.toCharArray());
    }

    public static boolean verify(String hash, String password) {
        return argon2.verify(hash, password.toCharArray());
    }
}