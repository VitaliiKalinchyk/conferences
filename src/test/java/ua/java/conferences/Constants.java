package ua.java.conferences;

import java.time.LocalDate;

public final class Constants {
    public static final long ID_VALUE = 1L;
    public static final int ZERO = 0;
    public static final int ONE = 1;
    public static final int THREE = 3;
    public static final int TEN = 10;
    public static final String EMAIL = "em@em.com";
    public static final String ANOTHER_EMAIL = "a@a.com";
    public static final String INCORRECT_EMAIL = "em.com";
    public static final String DUPLICATE = "Duplicate entry";
    public static final String PASSWORD = "Password1";
    public static final String ANOTHER_PASSWORD= "newPassword1234";
    public static final String INCORRECT_PASSWORD = "Pass1";
    public static final String NAME = "Joe";
    public static final String INCORRECT_NAME = "";
    public static final String SURNAME = "Yi";
    public static final String INCORRECT_SURNAME = "";
    public static final String SPEAKER_NAME = "Joe Yi";
    public static final String ROLE_VISITOR = "VISITOR";
    public static final int ROLE_ID = 4;
    public static final String TOPIC = "Report topic";
    public static final String INCORRECT_TOPIC = "";
    public static final String NAME_FIELD = "name";
    public static final String EMAIL_FIELD = "email";
    public static final String UPCOMING = "upcoming";
    public static final String PASSED = "passed";
    public static final String ASC = "ASC";
    public static final String DESC = "DESC";
    public static final String TITLE = "Event title";
    public static final String TITLE_FIELD = "title";
    public static final String ANOTHER_TITLE = "Another title";
    public static final String INCORRECT_TITLE = "";
    public static final LocalDate DATE = LocalDate.now().plusDays(ONE);
    public static final LocalDate INCORRECT_DATE = LocalDate.now().minusDays(ONE);
    public static final String DATE_NAME = DATE.toString();
    public static final String INCORRECT_DATE_NAME = INCORRECT_DATE.toString();
    public static final String LOCATION = "Kyiv";
    public static final String INCORRECT_LOCATION = "";
    public static final String DESCRIPTION = "What an awesome event!";
    public static final String INCORRECT_DESCRIPTION = "";
    public static final int REGISTRATIONS = 111;
    public static final int VISITORS = 99;
    public static final int REPORTS = 13;
    private Constants() {}
}