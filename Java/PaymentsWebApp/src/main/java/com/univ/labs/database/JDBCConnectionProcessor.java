package com.univ.labs.database;

import org.apache.log4j.Logger;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Анастасия on 23.05.2017.
 */
public class JDBCConnectionProcessor {
    private static final String URL = "jdbc:mysql://localhost:3306/payments";
    private static final String URL_FIXED = URL + "?useUnicode=true&useSSL=false&useJDBCCompliantTimezoneShift=true" +
            "&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "admin";

    private static Connection connection;
    private static Logger logger = Logger.getLogger(JDBCConnectionProcessor.class);

    private static void createDatabaseConnection() throws SQLException, NamingException {
        Driver driver = new com.mysql.cj.jdbc.Driver();
        DriverManager.registerDriver(driver);
        connection = DriverManager.getConnection(URL_FIXED, USER, PASSWORD);
        if (!connection.isClosed()) {
            logger.info("Connection to database 'payments' is established.");
        }
    }

    public static Connection getConnection() {
        try {
            createDatabaseConnection();
        } catch (SQLException | NamingException e) {
            logger.error(e.getMessage());
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            connection.close();
            if (connection.isClosed()) {
                logger.info("Database connection to 'payments' is closed");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }
}
