package ua.java.conferences.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static ua.java.conferences.utils.PasswordHashUtil.*;

class PasswordHashUtilTest {

    private static final String password = "PassWord1";

    private static final String wrongPassword = "Password1";

    @Test
    void testPasswordHashing() {
        String encoded = encode(password);
        assertNotEquals(password, encoded);
    }

    @Test
    void testVerifying() {
        String encoded = encode(password);
        assertTrue(verify(encoded, password));
    }

    @Test
    void testWrongPassword() {
        String encoded = encode(password);
        assertFalse(verify(encoded, wrongPassword));
    }
}