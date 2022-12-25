package ua.java.conferences.utils;

import javax.mail.*;
import javax.mail.internet.*;
import org.slf4j.*;

import java.io.*;
import java.util.Properties;

public class EmailSender {
    private static final Logger logger = LoggerFactory.getLogger(EmailSender.class);
    private static final String EMAIL_FILE = "email.properties";
    private static final String USER = "user";
    private static final String PASSWORD = "password";


    public void send(String subject, String body, String sendTo) {
        Properties properties = getProperties();
        String user = properties.getProperty(USER);
        String password = properties.getProperty(PASSWORD);
        Session session = getSession(properties, user, password);
        MimeMessage message = new MimeMessage(session);
        try {
            sendEmail(subject, body, sendTo, user, message);
        } catch (MessagingException e) {
            logger.error(e.getMessage());
        }
    }

    private void sendEmail(String subject, String body, String sendTo, String from, MimeMessage message)
            throws MessagingException {
        message.setFrom(new InternetAddress(from));
        message.setSubject(subject);
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(sendTo));
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        Multipart multipart = new MimeMultipart();
        mimeBodyPart.setContent(body, "text/html; charset=utf-8");
        multipart.addBodyPart(mimeBodyPart);
        message.setContent(multipart);
        Transport.send(message);
    }

    private Session getSession(Properties properties, String user, String password) {
        return Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });
    }

    private Properties getProperties() {
        Properties properties = new Properties();
        try (InputStream resource = EmailSender.class.getClassLoader().getResourceAsStream(EMAIL_FILE)){
            properties.load(resource);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return properties;
    }
}