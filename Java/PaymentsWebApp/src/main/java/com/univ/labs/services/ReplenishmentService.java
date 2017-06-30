package com.univ.labs.services;

import com.univ.labs.database.dao.AccountDAO;
import com.univ.labs.database.dao.CodeDAO;
import com.univ.labs.database.dao.CreditCardDAO;
import com.univ.labs.database.dao.DAOFactory;
import com.univ.labs.objects.Account;
import com.univ.labs.objects.CreditCard;

public class ReplenishmentService {
    private AccountDAO accountDAO = DAOFactory.getAccountDAO();
    private CreditCardDAO creditCardDAO = DAOFactory.getCreditCardDAO();
    private CodeDAO codeDAO = DAOFactory.getCodeDAO();
    public int getAmount(String code) {
        return codeDAO.getAmount(code);
    }

    public boolean replenish (String creditCardId, int amount) {
        CreditCard creditCard = creditCardDAO.getCreditCard(creditCardId);
        return accountDAO.replenish(creditCard.getAccountId(), amount);
    }

    public Account getAccount(String creditCardId) {
        CreditCard creditCard = creditCardDAO.getCreditCard(creditCardId);
        return accountDAO.getAccount(creditCard.getAccountId());
    }
}
