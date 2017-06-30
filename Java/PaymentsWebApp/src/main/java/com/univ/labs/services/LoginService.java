package com.univ.labs.services;

import com.univ.labs.database.dao.AccountDAO;
import com.univ.labs.database.dao.ClientDAO;
import com.univ.labs.database.dao.CreditCardDAO;
import com.univ.labs.database.dao.DAOFactory;
import com.univ.labs.objects.Account;
import com.univ.labs.objects.Client;

/**
 * Created by Анастасия on 25.06.2017.
 */
public class LoginService {
    private CreditCardDAO creditCardDAO = DAOFactory.getCreditCardDAO();
    private ClientDAO clientDAO = DAOFactory.getClientDAO();
    private AccountDAO accountDAO = DAOFactory.getAccountDAO();

    public boolean isValid(String creditCardNumber, String password) {
        return creditCardDAO.validateCreditCardUser(creditCardNumber, password);
    }

    public Client getClient(String creditCardNumber) {
        return clientDAO.getClient(creditCardNumber);
    }

    public Account getAccount(String creditCardNumber) {
        int accountId = creditCardDAO
                .getCreditCard(creditCardNumber)
                .getAccountId();
        return accountDAO.getAccount(accountId);
    }
}
