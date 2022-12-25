package ua.java.conferences.utils;

import javax.mail.*;
import javax.mail.internet.*;
import org.slf4j.*;

import java.io.*;
import java.util.Properties;

public class EmailSender {
    private static final Logger logger = LoggerFactory.getLogger(EmailSender.class);
    private static final String EMAIL_FILE = "email.properties";
    private static final Properties PROPERTIES = getProperties();
    private static final String USER = PROPERTIES.getProperty("user");
    private static final String PASSWORD = PROPERTIES.getProperty("password");
    private static final Session SESSION = getSession();


    public void send(String subject, String body, String sendTo) {
        MimeMessage message = new MimeMessage(SESSION);
        try {
            sendEmail(subject, body, sendTo, message);
        } catch (MessagingException e) {
            logger.error(e.getMessage());
        }
    }

    private void sendEmail(String subject, String body, String sendTo, MimeMessage message)
            throws MessagingException {
        message.setFrom(new InternetAddress(USER));
        message.setSubject(subject);
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(sendTo));
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        Multipart multipart = new MimeMultipart();
        mimeBodyPart.setContent(body, "text/html; charset=utf-8");
        multipart.addBodyPart(mimeBodyPart);
        message.setContent(multipart);
        Transport.send(message);
    }

    private static Session getSession() {
        return Session.getDefaultInstance(PROPERTIES, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USER, PASSWORD);
            }
        });
    }

    private static Properties getProperties() {
        Properties properties = new Properties();
        try (InputStream resource = EmailSender.class.getClassLoader().getResourceAsStream(EMAIL_FILE)){
            properties.load(resource);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return properties;
    }
}