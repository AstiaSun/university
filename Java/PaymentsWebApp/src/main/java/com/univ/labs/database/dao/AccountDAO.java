package com.univ.labs.database.dao;

import com.univ.labs.database.JDBCConnectionProcessor;
import com.univ.labs.objects.Account;
import com.univ.labs.objects.Client;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Account Database Access Object for working with 'accounts' table in database
 * Created by Анастасия on 23.05.2017.
 */
public class AccountDAO {
    private final Connection connection = JDBCConnectionProcessor.getConnection();
    private Logger logger = Logger.getLogger(AdministratorDAO.class);

    public Account getAccount(int accountId) {
        Account account = null;
        try {
            Statement statement = JDBCConnectionProcessor.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM  accounts WHERE ID='" + accountId + "';");
            if (resultSet.next()) {
                account = getAccountFromResultSet(resultSet);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCConnectionProcessor.closeConnection();
        }
        return account;
    }

    public List<Account> getClientAccounts(Client client) {
        List<Account> accounts = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM accounts " +
                    "JOIN credit_cards ON accounts.ID = credit_cards.ACCOUNT_ID " +
                    "JOIN clients ON credit_cards.CLIENT_ID = clients.ID " +
                    "WHERE CLIENT_ID='" + client.getId() + "';");
            accounts = getAccountsFromResultSet(resultSet);
            statement.close();
            logger.info("Accounts was found of client: " + client.getId());
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return accounts;
    }

    private List<Account> getAccountsFromResultSet(ResultSet resultSet) throws SQLException {
        List<Account> accounts = new ArrayList<Account>();
        while (resultSet.next()) {
            accounts.add(getAccountFromResultSet(resultSet));
        }
        return accounts;
    }

    private Account getAccountFromResultSet(ResultSet resultSet) throws SQLException {
        Account account = new Account();
        account.setId(resultSet.getInt("ID"));
        account.setBalance(resultSet.getFloat("BALANCE"));
        account.setBlocked(resultSet.getBoolean("IS_BLOCKED"));
        account.setCurrency(Account.Currency.valueOf(resultSet.getString("CURRENCY")));
        return account;
    }


    public boolean withdraw(int accountId, float amount) {
        if (isBlocked(accountId))
            return false;
        try {
            Statement statement = JDBCConnectionProcessor.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT accounts.* " +
                    "FROM  accounts WHERE ID='" + accountId + "';"
            );
            if (resultSet.next()) {
                Account account = getAccountFromResultSet(resultSet);
                statement.close();
                logger.info("Account was found: " + accountId);
                return withdrawFromAccount(account, amount);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            JDBCConnectionProcessor.closeConnection();
        }
        logger.warn("Account was not found: " + accountId);
        return false;
    }

    private boolean withdrawFromAccount(Account account, float amount) throws SQLException {
        if (account.getBalance() > amount) {
            Statement statement = JDBCConnectionProcessor.getConnection().createStatement();
            statement.executeUpdate("UPDATE accounts SET BALANCE='" + (account.getBalance() - amount)
                    + "' WHERE ID='" + account.getId() + "';"
            );
            statement.close();
            logger.info("Withdraw from account " + account.getId() + " sum " + amount);
            return true;
        }
        logger.warn("Cannot withdraw from account " + account.getId() + " sum " + amount);
        return false;
    }

    public boolean replenish(int accountId, float amount) {
        if (isBlocked(accountId))
            return false;
        try {
            Statement statement = JDBCConnectionProcessor.getConnection().createStatement();
            statement.executeUpdate("UPDATE accounts " +
                    "SET  BALANCE=BALANCE + '" + amount + "' WHERE ID='" + accountId + "';"
            );
            statement.close();
            return true;
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            JDBCConnectionProcessor.closeConnection();
        }
        return false;
    }

    public boolean block(int accountId) {
        if (isBlocked(accountId))
            return true;
        try {
            Statement statement = JDBCConnectionProcessor.getConnection().createStatement();
            statement.executeUpdate("UPDATE accounts SET IS_BLOCKED='" + 1 + "' WHERE ID='" + accountId + "';");
            statement.close();
            logger.info("Account was blocked: " + accountId);
            return true;
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            JDBCConnectionProcessor.closeConnection();
        }
        return false;
    }

    public boolean isBlocked(String creditCardId) {
        try {
            Statement statement = JDBCConnectionProcessor.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT accounts.IS_BLOCKED " +
                    "FROM accounts JOIN credit_cards WHERE credit_cards.ID='" + creditCardId + "';");
            if (resultSet.next()) {
                return resultSet.getBoolean(1);
            }
            statement.close();
            logger.info("Account is blocked for credit card: " + creditCardId);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            JDBCConnectionProcessor.closeConnection();
        }
        return true;
    }

    private boolean isBlocked(int accountId) {
        try {
            Statement statement = JDBCConnectionProcessor.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT IS_BLOCKED " +
                    "FROM accounts WHERE ID='" + accountId + "';");
            if (resultSet.next()) {
                return resultSet.getBoolean(1);
            }
            statement.close();
            logger.info("Account is blocked: " + accountId);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            JDBCConnectionProcessor.closeConnection();
        }
        return true;
    }
}