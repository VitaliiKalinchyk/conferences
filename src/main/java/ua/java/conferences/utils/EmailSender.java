package ua.java.conferences.utils;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import org.slf4j.*;

import java.io.*;
import java.util.Properties;

public class EmailSender {
    private static final Logger logger = LoggerFactory.getLogger(EmailSender.class);
    private static final String EMAIL_FILE = "email.properties";
    private static final String USER = "user";
    private static final String PASSWORD = "password";

    public void send(String sendTo, String subject, String messageToSend) {
        Properties properties = getProperties();
        String user = properties.getProperty(USER);
        String password = properties.getProperty(PASSWORD);
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(sendTo));
            message.setSubject(subject);
            message.setText(messageToSend);
            Transport.send(message);
        } catch (MessagingException e) {
            logger.error(e.getMessage());
        }
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