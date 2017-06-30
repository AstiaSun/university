package com.univ.labs.database.dao;

import com.univ.labs.database.JDBCConnectionProcessor;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class CodeDAO {
    private Logger logger = Logger.getLogger(CodeDAO.class);

    public int getAmount(String code) {
        try {
            Connection connection = JDBCConnectionProcessor.getConnection();
            int amount = getAmountFromDatabase(connection, code);
            removeCode(connection, code);
            logger.info("Account was found: " + code);
            return amount;
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            JDBCConnectionProcessor.closeConnection();
        }
        logger.warn("Account was not found: " + code);
        return 0;
    }

    private int getAmountFromDatabase(Connection connection, String code) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT amount FROM  codes WHERE code='" + code + "';");
        if (resultSet.next()) {
            logger.info("Code was found: " + code);
            return resultSet.getInt(1);
        }
        statement.close();
        logger.warn("Code was not found: " + code);
        return 0;
    }

    private void removeCode(Connection connection, String code) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("DELETE FROM codes WHERE code='" + code + "';");
        statement.close();
        logger.info("Code was removed: " + code);
    }
}
