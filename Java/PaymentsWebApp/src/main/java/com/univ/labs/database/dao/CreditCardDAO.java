package com.univ.labs.database.dao;

import com.univ.labs.database.JDBCConnectionProcessor;
import com.univ.labs.objects.Client;
import com.univ.labs.objects.CreditCard;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Credit Card Database Access Object for working with 'credit_cards' table in database
 * Created by Анастасия on 23.05.2017.
 */
public class CreditCardDAO {
    private final Connection connection = JDBCConnectionProcessor.getConnection();
    private Logger logger = Logger.getLogger(CreditCardDAO.class);

    public void addCreditCard(CreditCard creditCard) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute(
                "INSERT INTO credit_cards (ID, CLIENT_ID, ACCOUNT_ID, START_DATE, END_DATE) VALUES ('"
                        + creditCard.getNumber() + "', '" + creditCard.getClientId() + "', '" + creditCard.getAccountId()
                        + "', '" + creditCard.getStartDate() + "', '" + creditCard.getEndDate() + "');"
        );
        statement.close();
        logger.info("Credit card was added: " + creditCard);
    }

    public CreditCard getCreditCard(String cardId) {
        CreditCard creditCard = null;
        try {
            Statement statement = JDBCConnectionProcessor.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM credit_cards WHERE ID='" + cardId + "';");
            if (resultSet.next()) {
                creditCard = getCreditCardFromResultSet(resultSet);
            }
            statement.close();
            logger.info("Credit card was found: " + cardId);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            JDBCConnectionProcessor.closeConnection();
        }
        return creditCard;
    }

    public List<CreditCard> getClientCreditCards(Client client) {
        List<CreditCard> creditCards = new ArrayList<>();
        try {
            Statement statement = JDBCConnectionProcessor.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT credit_cards.* " +
                    "FROM credit_cards JOIN clients ON credit_cards.CLIENT_ID = clients.ID " +
                    "WHERE CLIENT_ID='" + client.getId() + "';");
            while (resultSet.next()) {
                CreditCard creditCard = getCreditCardFromResultSet(resultSet);
                creditCards.add(creditCard);
            }
            statement.close();
            logger.info("Credit cards was resolved for client " + client.getId());
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            JDBCConnectionProcessor.closeConnection();
        }
        return creditCards;
    }

    private CreditCard getCreditCardFromResultSet(ResultSet resultSet) throws SQLException {
        CreditCard creditCard = new CreditCard();
        creditCard.setAccountId(resultSet.getInt("ACCOUNT_ID"));
        creditCard.setClientId(resultSet.getInt("CLIENT_ID"));
        creditCard.setNumber(resultSet.getString("ID"));
        creditCard.setStartDate(resultSet.getString("START_DATE"));
        creditCard.setEndDate(resultSet.getString("END_DATE"));
        return creditCard;
    }

    public boolean validateCreditCardUser(String creditCardNumber, String password) {
        try {
            Statement statement = JDBCConnectionProcessor.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT CLIENT_ID FROM clients JOIN credit_cards on clients.ID = credit_cards.CLIENT_ID" +
                            " WHERE credit_cards.ID='" + creditCardNumber + "' AND clients.PASSWORD='" + password + "';"
            );
            if (resultSet.next()) {
                logger.info("Credit card was found: " + creditCardNumber);
                return true;
            }
            logger.warn("Credit card was not found: " + creditCardNumber);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            JDBCConnectionProcessor.closeConnection();
        }
        return false;
    }
}
