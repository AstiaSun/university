package com.univ.labs.database.dao;

import com.univ.labs.database.JDBCConnectionProcessor;
import com.univ.labs.objects.Administrator;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Administrator Database Access Object for working with administrators table in database
 * Created by Анастасия on 23.05.2017.
 */
public class AdministratorDAO {
    private final Logger logger = Logger.getLogger(AdministratorDAO.class);

    public void addAdministartor(Administrator administrator) throws SQLException {
        Statement statement = JDBCConnectionProcessor.getConnection().createStatement();
        statement.execute(
                "INSERT INTO administrators (PASSWORD) VALUES ('" + administrator.getPassword() + "');"
        );
        statement.close();
    }

    public Administrator getAdministrator(String email, String password) {
        Administrator administrator = null;
        try {
            administrator = getAdministratorFromQuery(email, password);
            logger.info("GET Administrator:\tOK");
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            JDBCConnectionProcessor.closeConnection();
        }
        return administrator;
    }

    private Administrator getAdministratorFromQuery(String email, String password) throws SQLException {
        Administrator administrator = null;
        Statement statement = JDBCConnectionProcessor.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM administrators " +
                "WHERE EMAIL='" + email + "' AND PASSWORD='" + password + "';");
        if (resultSet.next()) {
            administrator = getAdministratorFromResultSet(resultSet);
        }
        statement.close();
        return administrator;
    }

    private Administrator getAdministratorFromResultSet(ResultSet resultSet) {
        try {
            Administrator administrator = new Administrator();
            administrator.setId(resultSet.getInt("ID"));
            administrator.setEmail(resultSet.getString("EMAIL"));
            administrator.setPassword(resultSet.getString("PASSWORD"));
            return administrator;
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return null;
    }
}
