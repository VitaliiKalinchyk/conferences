package ua.java.conferences.utils;

import java.time.LocalDate;

public final class ValidatorUtil {

    private static final String EMAIL_REGEX = "^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$";

    private static final String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,20}$";

    private static final String NAME_REGEX = "^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє'\\- ]{1,30}";

    private static final String COMPLEX_NAME_REGEX = "^[\\wА-ЩЬЮЯҐІЇЄа-щьюяґіїє'.,;:+\\-~`!@#$^&*()={} ]{2,70}";

    private static final String DESCRIPTION_REGEX = "^[\\wА-ЩЬЮЯҐІЇЄа-щьюяґіїє'.,;:+\\-~`!@#$^&*()={} ]{1,200}";

    private ValidatorUtil(){}

    public static boolean validateEmail(String email) {
        return email.matches(EMAIL_REGEX);
    }

    public static boolean validatePassword(String password) {
        return password.matches(PASSWORD_REGEX);
    }

    public static boolean validateName(String name) {
        return name.matches(NAME_REGEX);
    }

    public static boolean validateComplexName(String name) {
        return name.matches(COMPLEX_NAME_REGEX);
    }

    public static boolean validateDate(LocalDate date) {
        return date.isAfter(LocalDate.now());
    }

    public static boolean validateDescription(String name) {
        return name.matches(DESCRIPTION_REGEX);
    }
}