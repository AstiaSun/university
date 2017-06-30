package com.univ.labs.database.dao;

import com.univ.labs.database.JDBCConnectionProcessor;
import com.univ.labs.objects.Client;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Client Database Access Object for working with 'clients' table in database
 * Created by Анастасия on 23.05.2017.
 */
public class ClientDAO {
    private final Logger logger = Logger.getLogger(ClientDAO.class);

    public void addClient(Client client) {
        try {
            Statement statement = JDBCConnectionProcessor.getConnection().createStatement();
            statement.execute(
                    "INSERT INTO clients (FIRSTNAME, LASTNAME, EMAIL, PASSWORD, PHONE, DATE_OF_BIRTH) VALUES ('"
                            + client.getFirstName() + "', '" + client.getLastName() + "', '"
                            + client.getEmail() + "', '" + client.getPassword() + "', '"
                            + client.getPhoneNumber() + "', '" + client.getDateOfBirth() + "');"
            );
            statement.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            JDBCConnectionProcessor.closeConnection();
        }
    }

    private Client getClientFromResultSet(ResultSet resultSet) throws SQLException {
        Client client = new Client();
        client.setId(resultSet.getInt("ID"));
        client.setFirstName(resultSet.getString("FIRSTNAME"));
        client.setLastName(resultSet.getString("LASTNAME"));
        client.setEmail(resultSet.getString("EMAIL"));
        client.setPassword(resultSet.getString("PASSWORD"));
        client.setPhoneNumber(resultSet.getString("PHONE"));
        client.setDateOfBirth(resultSet.getString("DATE_OF_BIRTH"));
        return client;
    }

    public Client getClient(String creditCardNumber) {
        Client client = null;
        try {
            Statement statement = JDBCConnectionProcessor.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM clients JOIN credit_cards ON " +
                    "clients.ID = credit_cards.CLIENT_ID WHERE credit_cards.ID='" + creditCardNumber + "';");
            if (resultSet.next()) {
                client = getClientFromResultSet(resultSet);
                logger.info("Client was found: " + client.getId());
            }
            statement.close();
            return client;
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            JDBCConnectionProcessor.closeConnection();
        }
        return null;
    }
}
