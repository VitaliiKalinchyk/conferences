package ua.java.conferences.utils;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static ua.java.conferences.utils.ValidatorUtil.*;

class ValidatorUtilTest {

    @Test
    void testValidateEmail() {
        String email = "karl.kory@amber.com.tv";
        assertTrue(validateEmail(email));
    }

    @Test
    void testValidateBadEmail() {
        String email = "karl.kory.amber.com.tv";
        assertFalse(validateEmail(email));

        email = "karl.kory@amber";
        assertFalse(validateEmail(email));

        email = "karl.kory@.amber";
        assertFalse(validateEmail(email));

        email = "@amber.com.tv";
        assertFalse(validateEmail(email));

        email = "karl.kory@amber.com.t";
        assertFalse(validateEmail(email));
    }

    @Test
    void testValidatePassword() {
        String password = "Password1";
        assertTrue(validatePassword(password));

        password = "Password1_";
        assertTrue(validatePassword(password));
    }

    @Test
    void testValidateBadPassword() {
        String password = "NoDigitPass";
        assertFalse(validatePassword(password));

        password = "no_upper_letters1";
        assertFalse(validatePassword(password));

        password = "NO_LOW_CASE_1";
        assertFalse(validatePassword(password));

        password = "Short1";
        assertFalse(validatePassword(password));

        password = "TooLongPassword1234567890";
        assertFalse(validatePassword(password));
    }

    @Test
    void testValidateName() {
        String name = "Joe Biden";
        assertTrue(validateName(name));

        name = "Залужний";
        assertTrue(validateName(name));

        name = "Квітка-Основ'яненко";
        assertTrue(validateName(name));
    }

    @Test
    void testValidateBadName() {
        String name = "Joe Biden 2";
        assertFalse(validateName(name));

        name = "Залужный";
        assertFalse(validateName(name));

        name = "Занадтодовгеім'ямаєбутинебільшетридцятисимволів";
        assertFalse(validateName(name));
    }

    @Test
    void testValidateComplexName() {
        String topic = "SQL. Introduction to SQL and JDBC";
        assertTrue(validateComplexName(topic));

        String title = "Java for Students Summer 2022";
        assertTrue(validateComplexName(title));

        String location = "Київ, вул. Хрещатик 1";
        assertTrue(validateComplexName(location));
    }

    @Test
    void testValidateBadComplexName() {
        String topic = "A";
        assertFalse(validateComplexName(topic));

        String title = "Java for Students Summer 2022. Занадто довге ім'я має бути не більше 70 символів";
        assertFalse(validateComplexName(title));
    }

    @Test
    void testValidateDate() {
        LocalDate localDate = LocalDate.now().plusDays(1);
        assertTrue(validateDate(localDate));
    }

    @Test
    void testValidateBadDate() {
        LocalDate localDate = LocalDate.now().minusDays(1);
        assertFalse(validateDate(localDate));
    }

    @Test
    void testValidateDescription() {
        String description = "Epam conference for Ukrainian students. Winter 2023";
        assertTrue(validateDescription(description));
    }

    @Test
    void testValidateBadDescription() {
        String description = "";
        assertFalse(validateDescription(description));
    }
}