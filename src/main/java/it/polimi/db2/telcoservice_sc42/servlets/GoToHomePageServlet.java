package it.polimi.db2.telcoservice_sc42.servlets;

import it.polimi.db2.telcoservice_sc42.entities.Order;
import it.polimi.db2.telcoservice_sc42.entities.ServicePackage;
import it.polimi.db2.telcoservice_sc42.services.OrderService;
import it.polimi.db2.telcoservice_sc42.services.PackageService;
import jakarta.ejb.EJB;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "GoToHomePageServlet", value = "/HomePage")
public class GoToHomePageServlet extends HttpServlet {

    @EJB(name = "it.polimi.db2.telcoservice_sc42.services/OrderService")
    OrderService orderService;

    @EJB(name = "it.polimi.db2.telcoservice_sc42.services/PackageService")
    PackageService packageService;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // If the user is not logged in (not present in session) redirect to the login
        String loginPath = getServletContext().getContextPath() + "/HTML/login.jsp";

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        if ( session.isNew() || username == null ) {
            response.sendRedirect(loginPath);
            return;
        }

        preparePackages(request);
        prepareRejectedOrders(request, username);
        response.sendRedirect(getServletContext().getContextPath() + "/HTML/home.jsp");
    }

    private void preparePackages(HttpServletRequest request) {
        // String[] test = {"Package 1", "Package 2", "Package 3", "Package 4"};
        List<ServicePackage> packages = packageService.findValidServicePackages();

        request.getSession().setAttribute("packages", packages);
    }

    private void prepareRejectedOrders(HttpServletRequest request, String username) {
        // String[] test = {"Order 1", "Order 2", "Order 3", "Order 4"};
        List<Order> rejectedOrders = orderService.findRejectedOrdersByClient(username);

        request.getSession().setAttribute("rejected", rejectedOrders);
    }
}
