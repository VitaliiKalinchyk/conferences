package ua.java.conferences.exceptions;

import static ua.java.conferences.exceptions.constants.Message.*;

public class CaptchaException extends ServiceException {
    public CaptchaException() {
        super(CAPTCHA_INVALID);
    }
}