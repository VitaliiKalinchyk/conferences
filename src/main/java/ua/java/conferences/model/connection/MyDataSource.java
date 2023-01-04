package ua.java.conferences.model.connection;

import static ua.java.conferences.model.connection.ConnectionConstants.*;

import com.zaxxer.hikari.*;
import org.slf4j.*;

import javax.sql.DataSource;
import java.io.*;
import java.util.Properties;

public class MyDataSource {
    private static final Logger logger = LoggerFactory.getLogger(MyDataSource.class);
    private static final String CONNECTION_FILE = "connection.properties";
    private static final HikariConfig config = new HikariConfig();
    private static final HikariDataSource ds;

    static {
        Properties properties = getProperties();
        config.setJdbcUrl(properties.getProperty(URL_PROPERTY));
        config.setUsername(properties.getProperty(USER_NAME));
        config.setPassword(properties.getProperty(PASSWORD));
        config.setDriverClassName(properties.getProperty(DRIVER));
        config.addDataSourceProperty(CACHE_PREPARED_STATEMENT, properties.getProperty(CACHE_PREPARED_STATEMENT));
        config.addDataSourceProperty(CACHE_SIZE, properties.getProperty(CACHE_SIZE));
        config.addDataSourceProperty(CACHE_SQL_LIMIT, properties.getProperty(CACHE_SQL_LIMIT));
        ds = new HikariDataSource(config);
    }

    private MyDataSource() {}

    public static DataSource getDataSource() {
        return ds;
    }

    private static Properties getProperties() {
        Properties properties = new Properties();
        try (InputStream resource = MyDataSource.class.getClassLoader().getResourceAsStream(CONNECTION_FILE)){
            properties.load(resource);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return properties;
    }
}