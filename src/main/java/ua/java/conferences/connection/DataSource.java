package ua.java.conferences.connection;

import static ua.java.conferences.connection.ConnectionConstants.*;

import com.zaxxer.hikari.*;

import java.io.*;
import java.sql.*;
import java.util.Properties;

public class DataSource {

    private static final HikariConfig config = new HikariConfig();

    private static final HikariDataSource ds;

    static {
        //loadDriver();
        Properties properties = getProperties();
        config.setJdbcUrl(properties.getProperty(URL_PROPERTY));
        config.setUsername(properties.getProperty(USER_NAME));
        config.setPassword(properties.getProperty(PASSWORD));
        config.addDataSourceProperty(CACHE_PREPARED_STATEMENT, properties.getProperty(CACHE_PREPARED_STATEMENT));
        config.addDataSourceProperty(CACHE_SIZE, properties.getProperty(CACHE_SIZE));
        config.addDataSourceProperty(CACHE_SQL_LIMIT, properties.getProperty(CACHE_SQL_LIMIT));
        ds = new HikariDataSource(config);
    }

    private DataSource() {}

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

//    private static void loadDriver() {
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//        } catch (ClassNotFoundException ignored) {}
//    }

    private static Properties getProperties() {
        Properties properties = new Properties();
        try (InputStream resource = DataSource.class.getClassLoader().getResourceAsStream("connection.properties")){
            properties.load(resource);
        } catch (IOException e) {
            // тут щось має бути наприклад логер;
        }
        return properties;
    }
}