package com.univ.labs.view;

import com.univ.labs.database.dao.AccountDAO;
import com.univ.labs.objects.Account;
import com.univ.labs.services.LoginService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "BlockServlet", urlPatterns = {"/block"})
public class BlockServlet extends HttpServlet {
    private LoginService loginService = new LoginService();
    private AccountDAO accountDAO = new AccountDAO();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext()
                .getRequestDispatcher("/jsp/block-action.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String confirm = req.getParameter("confirm");
        String creditCardId = (String) req.getSession().getAttribute("creditCard");
        Account account = loginService.getAccount(creditCardId);
        System.out.println(creditCardId);
        req.setAttribute("balance", account.getBalance());
        req.setAttribute("currency", account.getCurrency());
        System.out.println(confirm);
        if (confirm.equals("yes")) {
            if (accountDAO.block(account.getId())) {
                req.setAttribute("message", "Account successfully blocked!");
            } else {
                req.setAttribute("message", "Unable to block an account.");
            }
        } else {
            req.setAttribute("message", "Canceled.");
        }
        if (accountDAO.isBlocked(creditCardId)) {
            System.out.println(true);
        }
        getServletContext()
                .getRequestDispatcher("/jsp/user-actions.jsp")
                .forward(req, resp);
    }
}
