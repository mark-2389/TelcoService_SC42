package it.polimi.db2.telcoservice_sc42.web.servlets;

import it.polimi.db2.telcoservice_sc42.ejb.entities.Client;
import it.polimi.db2.telcoservice_sc42.ejb.entities.Employee;
import it.polimi.db2.telcoservice_sc42.exception.ClientNotFoundException;
import it.polimi.db2.telcoservice_sc42.exception.CredentialErrorException;
import it.polimi.db2.telcoservice_sc42.exception.NonUniqueClientException;
import it.polimi.db2.telcoservice_sc42.ejb.services.ClientService;
import it.polimi.db2.telcoservice_sc42.ejb.services.EmployeeService;
import it.polimi.db2.telcoservice_sc42.utils.BuySessionRegistry;
import it.polimi.db2.telcoservice_sc42.utils.SessionAttributeRegistry;
import jakarta.ejb.EJB;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet(name = "loginServlet", value = "/login")
public class LoginServlet extends HttpServlet {

    @EJB(name = "it.polimi.db2.telcoservice_sc42.ejb.services/LoginService")
    private ClientService clientService;

    @EJB(name = "it.polimi.db2.telcoservice_sc42.ejb.services/EmployeeService")
    private EmployeeService employeeService;

    public LoginServlet() {
        super();
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        handleRequest(req, resp);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        handleRequest(request, response);
    }

    private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().setAttribute(SessionAttributeRegistry.error, null);

        if ( request.getParameterValues("employeeLoginForm") != null )
            handleEmployeeRequest(request, response);
        else
            handleClientRequest(request, response);
    }

    private String getCaller(HttpServletRequest request) {
        if ( request.getParameterValues("landingLoginForm") != null ) {
            return "index.jsp";
        }

        if ( request.getParameterValues("loginForm") != null ) {
            return "HTML/login.jsp";
        }

        if ( request.getParameterValues("employeeLoginForm") != null ) {
            return "employee/login.jsp";
        }

        return null;
    }

    private void handleErrorRedirect(HttpServletRequest request, HttpServletResponse response, String error) throws IOException {
        String caller = getCaller(request);

        request.getSession().setAttribute(SessionAttributeRegistry.error, error);

        if ( caller != null ) {
            response.sendRedirect(request.getServletContext().getContextPath() + "/" + caller);
        }
    }

    private void handleEmployeeRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("id");
        String password = request.getParameter("password");

        System.out.println("Logging employee");

        Employee employee;

        try {
            employee = employeeService.checkCredentials(username, password);
        } catch (NonUniqueClientException | ClientNotFoundException | CredentialErrorException exception) {
            System.out.println(exception.getClass().getSimpleName());
            String error = exception.getClass().getSimpleName().replace("Exception", "");
            handleErrorRedirect(request, response, error);
            return;
        }

        System.out.println("Login ok");
        request.getSession().setAttribute(SessionAttributeRegistry.employeeId, employee.getUsername());
        response.sendRedirect(request.getServletContext().getContextPath() + "/HomePage");
    }

    private void handleClientRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Client client;

        try {
            client = clientService.checkCredentials(username, password);
        } catch (NonUniqueClientException | ClientNotFoundException | CredentialErrorException exception) {
            String error = exception.getClass().getSimpleName().replace("Exception", "");
            handleErrorRedirect(request, response, error);
            return;
        }

        request.getSession().setAttribute(SessionAttributeRegistry.username, client.getUsername());

        String redirectPage = "/HomePage";

        if ( request.getSession().getAttribute(BuySessionRegistry.selectedPackage) != null ) {
            // if a package has already been selected the user was in the confirmation page
            redirectPage = "/HTML/confirmation.jsp";
        }

        response.sendRedirect(request.getServletContext().getContextPath() + redirectPage);
    }

    public void destroy() {
    }
}