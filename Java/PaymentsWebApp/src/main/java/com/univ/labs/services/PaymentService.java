package com.univ.labs.services;

import com.univ.labs.database.dao.*;
import com.univ.labs.objects.Account;
import com.univ.labs.objects.CreditCard;
import com.univ.labs.objects.Payment;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PaymentService {
    private PaymentDAO paymentDAO = DAOFactory.getPaymentDAO();
    private CreditCardDAO creditCardDAO = DAOFactory.getCreditCardDAO();
    private AccountDAO accountDAO = DAOFactory.getAccountDAO();

    public void completePayment(HttpServletRequest request, HttpServletResponse response) {
        Payment payment = createPayment(request, response);
        if (payment == null) {
            request.setAttribute("message", "Failed to complete a payment");
            CreditCard creditCard = creditCardDAO.getCreditCard((String) request.getSession().getAttribute("creditCard"));
            Account account = accountDAO.getAccount(creditCard.getAccountId());
            request.setAttribute("balance", account.getBalance());
            request.setAttribute("currency", account.getCurrency());
            return;
        }
        if (accountDAO.withdraw(payment.getSourceAccountId(), payment.getPrice())) {
            accountDAO.replenish(payment.getTargetAccountId(), payment.getPrice());
            request.setAttribute("message", "Payment completed successfully!");
            paymentDAO.addPayment(payment);
        } else {
            request.setAttribute("message", "Not enough money.");
        }
        Account account = accountDAO.getAccount(payment.getSourceAccountId());
        request.setAttribute("balance", account.getBalance());
        request.setAttribute("currency", account.getCurrency());
    }

    private Payment createPayment(HttpServletRequest request, HttpServletResponse response) {
        Payment payment = new Payment();
        String sourceCreditCardId = request.getParameter("sourceCreditCardNumber");
        String targetCreditCardId = request.getParameter("targetCreditCardNumber");
        CreditCard sourceCreditCard = creditCardDAO.getCreditCard(sourceCreditCardId);
        CreditCard targetCreditCard = creditCardDAO.getCreditCard(targetCreditCardId);
        if ((sourceCreditCard == null) || (targetCreditCard == null))
            return null;
        payment.setSourceAccountId(sourceCreditCard.getAccountId());
        payment.setTargetAccountId(targetCreditCard.getAccountId());
        String amount = request.getParameter("amount");
        payment.setPrice(Float.valueOf(amount));
        payment.setDateOfPayment(getCurrentDate());
        return payment;
    }

    private String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(new Date());
    }
}
