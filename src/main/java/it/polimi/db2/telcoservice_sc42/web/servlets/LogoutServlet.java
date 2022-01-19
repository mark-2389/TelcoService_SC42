package it.polimi.db2.telcoservice_sc42.web.servlets;

import it.polimi.db2.telcoservice_sc42.utils.SessionAttributeRegistry;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "logoutServlet", value = "/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("Logging out");

        request.getSession().setAttribute(SessionAttributeRegistry.error, null);

        // get the login page before invalidating the session
        String loginPage = getLoginPage(request);

        // request.getSession(false) returns null if no session exists
        // request.getSession(true) creates and returns a new session if no session exists
        HttpSession session = request.getSession(false);

        if ( session != null ) {
            session.invalidate();
        }

        response.sendRedirect(request.getServletContext().getContextPath() + loginPage);
    }

    private String getLoginPage(HttpServletRequest request) {
        if ( request.getSession().getAttribute(SessionAttributeRegistry.employeeId) != null ) {
            return "/employee/login.jsp";
        }

        return "/index.jsp";
    }

}
