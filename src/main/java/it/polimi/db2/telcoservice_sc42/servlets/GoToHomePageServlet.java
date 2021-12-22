package it.polimi.db2.telcoservice_sc42.servlets;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "GoToHomePageServlet", value = "/HomePage")
public class GoToHomePageServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // If the user is not logged in (not present in session) redirect to the login
        String loginPath = getServletContext().getContextPath() + "/HTML/login.jsp";

        HttpSession session = request.getSession();
        if (session.isNew() || session.getAttribute("username") == null) {
            response.sendRedirect(loginPath);
            return;
        }

        preparePackages(request);
        prepareRejectedOrders(request);
        response.sendRedirect(getServletContext().getContextPath() + "/HTML/home.jsp");
    }

    private void preparePackages(HttpServletRequest request) {
        // TODO: replace with actual packages from db
        String[] packages = {"Package 1", "Package 2", "Package 3", "Package 4"};

        request.getSession().setAttribute("packages", packages);
    }

    private void prepareRejectedOrders(HttpServletRequest request) {
        // TODO: replace with actual rejected orders from db
        String[] rejectedOrders = {"Order 1", "Order 2", "Order 3", "Order 4"};

        request.getSession().setAttribute("rejected", rejectedOrders);
    }
}
