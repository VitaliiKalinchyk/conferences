package ua.java.conferences.utils;

import ua.java.conferences.exceptions.NoSuchEventException;
import ua.java.conferences.exceptions.NoSuchReportException;
import ua.java.conferences.exceptions.NoSuchUserException;
import ua.java.conferences.exceptions.ServiceException;

import java.time.LocalDate;

public final class ValidatorUtil {

    private static final String EMAIL_REGEX = "^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$";

    private static final String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,20}$";

    //For names and surnames
    private static final String NAME_REGEX = "^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє'\\- ]{1,30}";

    //for topics and titles and locations
    private static final String COMPLEX_NAME_REGEX = "^[\\wА-ЩЬЮЯҐІЇЄа-щьюяґіїє'.,;:+\\-~`!@#$^&*()={} ]{2,70}";

    private static final String DESCRIPTION_REGEX = "^[\\wА-ЩЬЮЯҐІЇЄа-щьюяґіїє'.,;:+\\-~`!@#$^&*()={} ]{1,200}";

    private ValidatorUtil(){}

    public static boolean validateEmail(String email) {
        return email != null &&email.matches(EMAIL_REGEX);
    }

    public static boolean validatePassword(String password) {
        return password != null && password.matches(PASSWORD_REGEX);
    }

    public static boolean validateName(String name) {
        return name != null && name.matches(NAME_REGEX);
    }

    public static boolean validateComplexName(String name) {
        return name != null && name.matches(COMPLEX_NAME_REGEX);
    }

    public static boolean validateDate(LocalDate date) {
        return date != null && date.isAfter(LocalDate.now());
    }

    public static boolean validateDescription(String name) {
        return name != null && name.matches(DESCRIPTION_REGEX);
    }

    public static long getEventId(String longString) throws NoSuchEventException {
        long eventId;
        try {
            eventId = Long.parseLong(longString);
        } catch (NumberFormatException e) {
            throw new NoSuchEventException();
        }
        return eventId;
    }

    public static long getReportId(String longString) throws NoSuchReportException {
        long reportId;
        try {
            reportId = Long.parseLong(longString);
        } catch (NumberFormatException e) {
            throw new NoSuchReportException();
        }
        return reportId;
    }

    public static long getUserId(String longString) throws NoSuchUserException {
        long userId;
        try {
            userId = Long.parseLong(longString);
        } catch (NumberFormatException e) {
            throw new NoSuchUserException();
        }
        return userId;
    }

    public static long getLong(String longString) throws ServiceException {
        long result;
        try {
            result = Long.parseLong(longString);
        } catch (NumberFormatException e) {
            throw new ServiceException();
        }
        return result;
    }

    public static int getInt(String intString) throws ServiceException {
        int result;
        try {
            result = Integer.parseInt(intString);
        } catch (NumberFormatException e) {
            throw new ServiceException();
        }
        return result;
    }

    public static void checkString(String... strings) throws ServiceException {
        for (String string : strings) {
            if (string == null) {
                throw new ServiceException(new NullPointerException());
            }
        }
    }
}