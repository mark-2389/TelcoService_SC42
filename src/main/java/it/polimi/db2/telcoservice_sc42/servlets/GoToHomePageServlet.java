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
        if ( request.getSession().getAttribute("id") != null ) {
            prepareEmployeeHome(request, response);
        } else {
            prepareClientHome(request, response);
        }
    }

    /**
     * Check if the request should be rejected and redirect it if that's the case.
     *
     * @param request the http request.
     * @param response the http response.
     * @param redirectPath the path to which the request should be redirected. The path starts from the servletContext
     *                     contextPath and should not contain the leading "/" character.
     * @param attributeName the name of the session's attribute that contains the username (or id) of the user that is
     *                      logging in.
     * @return the user's username (or id in case of Employee) if the no reject redirect was needed, null otherwise.
     * @throws IOException if the redirect fails.
     */
    private String handleRejectRequest(HttpServletRequest request, HttpServletResponse response, String redirectPath, String attributeName) throws IOException {
        HttpSession session = request.getSession();

        String username = (String) session.getAttribute(attributeName);
        if ( session.isNew() || username == null ) {
            String loginPath = getServletContext().getContextPath() + "/" + redirectPath;
            response.sendRedirect(loginPath);
        }

        return username;
    }

    /**
     * Prepare the home page for the client and redirect to it.
     *
     * @param request the http request.
     * @param response the http response.
     * @throws IOException if the redirect fails.
     */
    private void prepareClientHome(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = handleRejectRequest(request, response, "HTML/login.jsp", "username");

        if ( username != null ) {
            preparePackages(request);
            prepareRejectedOrders(request, username);
            response.sendRedirect(getServletContext().getContextPath() + "/" + "HTML/home.jsp");
        }
    }

    /**
     * Prepare the home page for the client and redirect to it.
     *
     * @param request the http request.
     * @param response the http response.
     * @throws IOException if the redirect fails.
     */
    private void prepareEmployeeHome(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = handleRejectRequest(request, response, "employee/login.jsp", "id");

        if ( id != null ) {
            response.sendRedirect(getServletContext().getContextPath() + "/" + "employee/home.jsp");
        }
    }

    private void preparePackages(HttpServletRequest request) {
        List<ServicePackage> packages = packageService.findValidServicePackages();

        request.getSession().setAttribute("packages", packages);
    }

    private void prepareRejectedOrders(HttpServletRequest request, String username) {
        List<Order> rejectedOrders = orderService.findRejectedOrdersByClient(username);

        request.getSession().setAttribute("rejected", rejectedOrders);
    }
}
