package com.univ.labs.view;

import com.univ.labs.objects.Account;
import com.univ.labs.services.ReplenishmentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "ReplenishmentServlet",
        urlPatterns = {"/replenish"}
)
public class ReplenishmentServlet extends HttpServlet {
    private ReplenishmentService replenishmentService = new ReplenishmentService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext()
                .getRequestDispatcher("/jsp/replenishment-action.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String creditCardNumber = request.getParameter("creditCardNumber");
        String code = request.getParameter("code");
        int amount = replenishmentService.getAmount(code);
        if (amount != 0 && replenishmentService.replenish(creditCardNumber, amount)) {
            request.setAttribute("message", "Success!");
        } else {
            request.setAttribute("message", "Invalid code or/and credit card number.");
        }
        Account account = replenishmentService.getAccount(creditCardNumber);
        request.setAttribute("balance", account.getBalance());
        request.setAttribute("currency", account.getCurrency());
        getServletContext()
                .getRequestDispatcher("/jsp/user-actions.jsp")
                .forward(request, response);
    }
}
