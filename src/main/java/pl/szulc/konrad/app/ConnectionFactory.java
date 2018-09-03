package pl.szulc.konrad.app;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * All needed stuff to connect using JDBC only.
 */
public class ConnectionFactory {

    private MysqlDataSource dataSource;

    public ConnectionFactory(String fileProperties) {
        try {
            InputStream inputStream = ConnectionFactory.class.getResourceAsStream(fileProperties);

            if (inputStream==null){
                throw new IllegalArgumentException("databases.properties not found");
            }
            Properties properties = new Properties();
            properties.load(inputStream);
            dataSource = new MysqlDataSource();
            dataSource.setServerName(properties.getProperty("pl.sda.jdbc.db.server"));
            dataSource.setDatabaseName(properties.getProperty("pl.sda.jdbc.db.name"));
            dataSource.setUser(properties.getProperty("pl.sda.jdbc.db.user"));
            dataSource.setPassword(properties.getProperty("pl.sda.jdbc.db.password"));
            String portStr = properties.getProperty("pl.sda.jdbc.db.port");
            dataSource.setPort(Integer.parseInt(portStr));
            dataSource.setServerTimezone("Europe/Warsaw");
            dataSource.setUseSSL(false);
            dataSource.setCharacterEncoding("UTF-8");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public Connection getConnection() throws SQLException {
        try {
            return dataSource.getConnection();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}