package com.univ.labs.database.dao;

/**
 * Created by Анастасия on 23.05.2017.
 */
public class DAOFactory {

    public static AccountDAO getAccountDAO() {
        return new AccountDAO();
    }

    public static AdministratorDAO getAdministratorDAO() {
        return new AdministratorDAO();
    }

    public static ClientDAO getClientDAO() {
        return new ClientDAO();
    }

    public static CreditCardDAO getCreditCardDAO() {
        return new CreditCardDAO();
    }

    public static PaymentDAO getPaymentDAO() {
        return new PaymentDAO();
    }

    public static CodeDAO getCodeDAO() { return new CodeDAO(); }
}
