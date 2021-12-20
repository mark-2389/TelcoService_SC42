package it.polimi.db2.telcoservice_sc42.servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "GoToHomePageServlet", value = "/HomePage")
public class GoToHomePageServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // If the user is not logged in (not present in session) redirect to the login
        String loginPath = getServletContext().getContextPath() + "/WEB-INF/home.jsp";
        HttpSession session = request.getSession();
        //if (session.isNew() || session.getAttribute("user") == null) {
            response.sendRedirect(loginPath);
        //    return;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
