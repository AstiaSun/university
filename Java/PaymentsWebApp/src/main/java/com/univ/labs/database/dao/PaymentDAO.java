package com.univ.labs.database.dao;


import com.univ.labs.database.JDBCConnectionProcessor;
import com.univ.labs.objects.Payment;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * Created by Анастасия on 23.05.2017.
 */
public class PaymentDAO {
    private Logger logger = Logger.getLogger(PaymentDAO.class);
    public void addPayment(Payment payment) {
        try {
            Statement statement = JDBCConnectionProcessor.getConnection().createStatement();
            statement.execute(
                    "INSERT INTO payment (PRICE, SOURCE_ACCOUNT, TARGET_ACCOUNT, DATE_OF_PAYMENT) VALUES ('"
                            + payment.getPrice() + "', '" + payment.getSourceAccountId() + "', '"
                            + payment.getTargetAccountId() + "', '" + payment.getDateOfPayment() + "');"
            );
            statement.close();
            logger.info("New payment was added.");
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            JDBCConnectionProcessor.closeConnection();
        }
    }
}
