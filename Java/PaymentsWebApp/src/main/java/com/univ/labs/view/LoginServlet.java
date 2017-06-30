package com.univ.labs.view;

import com.univ.labs.objects.Account;
import com.univ.labs.objects.Client;
import com.univ.labs.services.LoginService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "LoginServlet",
        urlPatterns = {"/login"}
)
public class LoginServlet extends HttpServlet {
    private LoginService loginService = new LoginService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String creditCardId = (String) request.getSession().getAttribute("creditCard");
        if ((creditCardId != null) && (!creditCardId.equals(""))) {
            request.getSession().setAttribute("creditCard", "");
        }
        getServletContext()
                .getRequestDispatcher("/jsp/login.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action.equals("login")) {
            login(request, response);
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String creditCardNumber = request.getParameter("number");
        String password = request.getParameter("password");
        if (loginService.isValid(creditCardNumber, password)) {
            request.getSession().setAttribute("creditCard", creditCardNumber);
            successfulLogin(request, response);
        } else {
            failedLogin(request, response);
        }
    }

    private void successfulLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String message = "Success!";
        request.setAttribute("message", message);
        String creditCardNumber = (String) request.getSession().getAttribute("creditCard");
        Client client = loginService.getClient(creditCardNumber);
        request.getSession().setAttribute("firstName", client.getFirstName());
        request.getSession().setAttribute("lastName", client.getLastName());
        Account account = loginService.getAccount(creditCardNumber);
        request.setAttribute("balance", account.getBalance());
        request.setAttribute("currency", account.getCurrency());
        request.getSession().setAttribute("email", client.getEmail());
        forward(request, response, "/jsp/user-actions.jsp");

    }

    private void forward(HttpServletRequest request, HttpServletResponse response, String nextJSP) throws ServletException, IOException {
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        dispatcher.forward(request, response);
    }

    private void failedLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String message = "Wrong credit card number or password.";
        request.setAttribute("message", message);
        forward(request, response, "/jsp/login.jsp");
    }
}