package ua.java.conferences.utils;

import org.junit.jupiter.api.Test;
import ua.java.conferences.exceptions.IncorrectFormatException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static ua.java.conferences.exceptions.constants.Message.*;
import static ua.java.conferences.utils.ValidatorUtil.*;

class ValidatorUtilTest {

    @Test
    void testValidateEmail() {
        String email = "karl.kory@amber.com.tv";
        assertDoesNotThrow(() -> validateEmail(email));
    }

    @Test
    void testValidateBadEmail() {
        String email = "karl.kory.amber.com.tv";
        IncorrectFormatException exception = assertThrows(IncorrectFormatException.class, () -> validateEmail(email));
        assertEquals(ENTER_CORRECT_EMAIL, exception.getMessage());

        String email2 = "karl.kory@amber";
        assertThrows(IncorrectFormatException.class, () -> validateEmail(email2));

        String email3 = "karl.kory@.amber";
        assertThrows(IncorrectFormatException.class, () -> validateEmail(email3));

        String email4 = "@amber.com.tv";
        assertThrows(IncorrectFormatException.class, () -> validateEmail(email4));

        String email5 = "karl.kory@amber.com.t";
        assertThrows(IncorrectFormatException.class, () -> validateEmail(email5));
    }

    @Test
    void testValidatePassword() {
        String password = "Password1";
        assertDoesNotThrow(() -> validatePassword(password));

        String password2 = "Password1_";
        assertDoesNotThrow(() -> validatePassword(password2));
    }

    @Test
    void testValidateBadPassword() {
        String password = "NoDigitPass";
        IncorrectFormatException exception = assertThrows(IncorrectFormatException.class, () -> validatePassword(password));
        assertEquals(ENTER_CORRECT_PASSWORD, exception.getMessage());

        String password2 = "no_upper_letters1";
        assertThrows(IncorrectFormatException.class, () -> validatePassword(password2));

        String password3 = "NO_LOW_CASE_1";
        assertThrows(IncorrectFormatException.class, () -> validatePassword(password3));

        String password4 = "Short1";
        assertThrows(IncorrectFormatException.class, () -> validatePassword(password4));

        String password5 = "TooLongPassword1234567890";
        assertThrows(IncorrectFormatException.class, () -> validatePassword(password5));
    }

    @Test
    void testValidateName() {
        String name = "Joe Biden";
        assertDoesNotThrow(() -> validateName(name, ENTER_CORRECT_NAME));

        String name2 = "Залужний";
        assertDoesNotThrow(() -> validateName(name2, ENTER_CORRECT_NAME));

        String name3 = "Квітка-Основ'яненко";
        assertDoesNotThrow(() -> validateName(name3, ENTER_CORRECT_NAME));
    }

    @Test
    void testValidateBadName() {
        String name = "Joe Biden 2";
        IncorrectFormatException exception =
                assertThrows(IncorrectFormatException.class, () -> validateName(name, ENTER_CORRECT_NAME));
        assertEquals(ENTER_CORRECT_NAME, exception.getMessage());

        String name2 = "Залужный";
        assertThrows(IncorrectFormatException.class, () -> validateName(name2, ENTER_CORRECT_NAME));

        String name3 = "Занадтодовгеім'ямаєбутинебільшетридцятисимволів";
        assertThrows(IncorrectFormatException.class, () -> validateName(name3, ENTER_CORRECT_NAME));
    }

    @Test
    void testValidateComplexName() {
        String topic = "SQL. Introduction to SQL and JDBC";
        assertDoesNotThrow(() -> validateComplexName(topic, ENTER_CORRECT_TOPIC));

        String title = "Java for Students Summer 2022";
        assertDoesNotThrow(() -> validateComplexName(title, ENTER_CORRECT_TITLE));

        String location = "Київ, вул. Хрещатик 1";
        assertDoesNotThrow(() -> validateComplexName(location, ENTER_CORRECT_LOCATION));
    }

    @Test
    void testValidateBadComplexName() {
        String topic = "A";
        IncorrectFormatException exception =
                assertThrows(IncorrectFormatException.class, () -> validateComplexName(topic, ENTER_CORRECT_TOPIC));
        assertEquals(ENTER_CORRECT_TOPIC, exception.getMessage());

        String title = "Java for Students Summer 2022. Занадто довге ім'я має бути не більше 70 символів";
        exception = assertThrows(IncorrectFormatException.class, () -> validateComplexName(title, ENTER_CORRECT_TITLE));
        assertEquals(ENTER_CORRECT_TITLE, exception.getMessage());
    }

    @Test
    void testValidateDate() {
        LocalDate localDate = LocalDate.now().plusDays(1);
        assertDoesNotThrow(() -> validateDate(localDate));
    }

    @Test
    void testValidateBadDate() {
        LocalDate localDate = LocalDate.now().minusDays(1);
        IncorrectFormatException exception = assertThrows(IncorrectFormatException.class, () -> validateDate(localDate));
        assertEquals(ENTER_VALID_DATE, exception.getMessage());
    }

    @Test
    void testValidateDescription() {
        String description = "Epam conference for Ukrainian students. Winter 2023";
        assertDoesNotThrow(() -> validateDescription(description));
    }

    @Test
    void testValidateBadDescription() {
        String description = "";
        IncorrectFormatException exception =
                assertThrows(IncorrectFormatException.class, () -> validateDescription(description));
        assertEquals(ENTER_CORRECT_DESCRIPTION, exception.getMessage());
    }
}