package ua.java.conferences.utils;

import ua.java.conferences.exceptions.*;
import java.time.LocalDate;

import static ua.java.conferences.exceptions.constants.Message.*;
import static ua.java.conferences.utils.constants.Regex.*;

public final class ValidatorUtil {

    private ValidatorUtil() {}

    public static void validateEmail(String email) throws IncorrectFormatException {
        validateFormat(email, EMAIL_REGEX, ENTER_CORRECT_EMAIL);
    }

    public static void validatePassword(String password) throws IncorrectFormatException {
        validateFormat(password, PASSWORD_REGEX, ENTER_CORRECT_PASSWORD);
    }


    public static void validateName(String name, String message) throws IncorrectFormatException {
        validateFormat(name, NAME_REGEX, message);
    }

    public static void validateComplexName(String name, String message) throws IncorrectFormatException {
        validateFormat(name, COMPLEX_NAME_REGEX, message);
    }

    public static void validateDescription(String name) throws IncorrectFormatException {
        validateFormat(name, DESCRIPTION_REGEX, ENTER_CORRECT_DESCRIPTION);
    }

    private static void validateFormat(String name, String regex,String message) throws IncorrectFormatException {
        if (name == null || !name.matches(regex))
            throw new IncorrectFormatException(message);
    }

    public static void validateDate(LocalDate date) throws IncorrectFormatException {
        if (date == null || !date.isAfter(LocalDate.now())) {
            throw new IncorrectFormatException(ENTER_VALID_DATE);
        }
    }

    public static long getEventId(String idString) throws ServiceException {
        return checkId(idString, new NoSuchEventException());
    }


    public static long getReportId(String idString) throws ServiceException {
        return checkId(idString, new NoSuchReportException());

    }

    public static long getUserId(String idString) throws ServiceException {
        return checkId(idString, new NoSuchUserException());
    }

    private static long checkId(String idString, ServiceException exception) throws ServiceException {
        long eventId;
        try {
            eventId = Long.parseLong(idString);
        } catch (NumberFormatException e) {
            throw exception;
        }
        return eventId;
    }

    public static long getLong(String longString) throws ServiceException {
        long result;
        try {
            result = Long.parseLong(longString);
        } catch (NumberFormatException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    public static int getInt(String intString) throws ServiceException {
        int result;
        try {
            result = Integer.parseInt(intString);
        } catch (NumberFormatException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    public static void checkStrings(String... strings) throws ServiceException {
        for (String string : strings) {
            if (string == null) {
                throw new ServiceException(new NullPointerException());
            }
        }
    }
}