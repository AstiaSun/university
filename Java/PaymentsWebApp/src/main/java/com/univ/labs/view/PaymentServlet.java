package com.univ.labs.view;

import com.univ.labs.services.PaymentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Анастасия on 25.06.2017.
 */
@WebServlet (
        name = "PaymentServlet",
        urlPatterns = {"/payment"}
)
public class PaymentServlet extends HttpServlet {
    private PaymentService paymentService = new PaymentService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext()
                .getRequestDispatcher("/jsp/payment-action.jsp")
                .forward(request, response);
    }

    protected void doPost(HttpServletRequest request ,HttpServletResponse response) throws ServletException, IOException {
        paymentService.completePayment(request, response);
        getServletContext()
                .getRequestDispatcher("/jsp/user-actions.jsp")
                .forward(request, response);
    }
}
