package ua.java.conferences.utils;

import jakarta.json.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.java.conferences.exceptions.CaptchaException;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.*;

public class Captcha {
    private static final Logger logger = LoggerFactory.getLogger(Captcha.class);
    public static final String METHOD = "POST";
    public static final String URL = "https://www.google.com/recaptcha/api/siteverify";
    public static final String SECRET = "6LecrqsjAAAAAFXiIRGsirnaY2A7fzPpTlj0tWdr";
    private static final String USER_AGENT = "Mozilla/5.0";
    public void verify(String gRecaptchaResponse) throws CaptchaException {
        try{
            URL url = new URL(URL);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            setupConnection(connection);
            writeOutput(gRecaptchaResponse, connection);
            StringBuilder response = getResponse(connection);
            checkIfCaptchaPassed(response);
        } catch(IllegalStateException | UnknownHostException e){
            logger.error(e.getMessage());
        } catch(Exception e){
            logger.error(e.getMessage());
            throw new CaptchaException();
        }
    }

    private static void checkIfCaptchaPassed(StringBuilder response) throws CaptchaException {
        try (JsonReader jsonReader = Json.createReader(new StringReader(response.toString()))) {
            JsonObject jsonObject = jsonReader.readObject();
            if (!jsonObject.getBoolean("success")) {
                throw new CaptchaException();
            }
        }
    }

    private static StringBuilder getResponse(HttpsURLConnection connection) throws IOException {
        StringBuilder response = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                response.append(inputLine);
            }
        }
        return response;
    }

    private static void writeOutput(String gRecaptchaResponse, HttpsURLConnection connection) throws IOException {
        String postParams = "secret=" + SECRET + "&response=" + gRecaptchaResponse;
        try (DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream())) {
            outputStream.writeBytes(postParams);
            outputStream.flush();
        }
    }

    private static void setupConnection(HttpsURLConnection connection) throws ProtocolException {
        connection.setRequestMethod(METHOD);
        connection.setRequestProperty("User-Agent", USER_AGENT);
        connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        connection.setDoOutput(true);
    }
}