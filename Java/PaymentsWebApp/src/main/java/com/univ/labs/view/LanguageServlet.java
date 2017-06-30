package com.univ.labs.view;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Andrey on 04/08/2017.
 */
@WebServlet(name = "LanguageServlet", urlPatterns = {"/lang/ru", "/lang/en"})
public class LanguageServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        HttpSession session = req.getSession();
        String url = req.getRequestURL().toString();
        String locale = url.endsWith("ru") ? "ru_RU" : "en_US";
        session.setAttribute("locale", locale);
        res.sendRedirect("/");
    }
}
