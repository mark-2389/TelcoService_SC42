package it.polimi.db2.telcoservice_sc42.servlets;

import java.io.*;

import it.polimi.db2.telcoservice_sc42.entities.Client;
import it.polimi.db2.telcoservice_sc42.entities.Employee;
import it.polimi.db2.telcoservice_sc42.exception.ClientNotFoundException;
import it.polimi.db2.telcoservice_sc42.exception.NonUniqueClientException;
import it.polimi.db2.telcoservice_sc42.services.ClientService;
import it.polimi.db2.telcoservice_sc42.services.EmployeeService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;


@WebServlet(name = "loginServlet", value = "/login")
public class LoginServlet extends HttpServlet {

    @EJB(name = "it.polimi.db2.telcoservice_sc42.services/LoginService")
    private ClientService clientService;

    @EJB(name = "it.polimi.db2.telcoservice_sc42.services/EmployeeService")
    private EmployeeService employeeService;

    public LoginServlet() {
        super();
    }

    public void init() throws ServletException {
        super.init();
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("POST called");
        handleRequest(req, resp);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        handleRequest(request, response);
    }

    private String getCaller(HttpServletRequest request) {
        if ( request.getParameterValues("landingLoginForm") != null ) {
            return "index.jsp";
        }

        if ( request.getParameterValues("loginForm") != null ) {
            return "HTML/login.jsp";
        }

        return null;
    }

    private void handleErrorRedirect(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String caller = getCaller(request);

        if ( caller != null ) {
            response.sendRedirect(request.getServletContext().getContextPath() + caller);
        }
    }

    private void handleEmployeeRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Employee employee;

        try {
            employee = employeeService.checkCredentials(username, password);
        } catch (NonUniqueClientException | ClientNotFoundException exception) {
            handleErrorRedirect(request, response);
            return;
        }

        request.getSession().setAttribute("username", employee.getUsername());
        request.getSession().setAttribute("employee", true);
        response.sendRedirect(request.getServletContext().getContextPath() + "/HomePage");
    }

    private void handleClientRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Client client;

        try {
            client = clientService.checkCredentials(username, password);
        } catch (NonUniqueClientException | ClientNotFoundException exception) {
            handleErrorRedirect(request, response);
            return;
        }

        request.getSession().setAttribute("username", client.getUsername());
        request.getSession().setAttribute("employee", false);
        response.sendRedirect(request.getServletContext().getContextPath() + "/HomePage");
    }

    private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        handleClientRequest(request, response);
        // handleEmployeeRequest(request, response);
    }

    private void handle(HttpServletRequest request, HttpServletResponse response, UsernameGenerator generator) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        String name;

        try {
            name = generator.getUsername(username, password);
        } catch (NonUniqueClientException | ClientNotFoundException exception) {
            handleErrorRedirect(request, response);
            return;
        }

        request.getSession().setAttribute("username", name);
        response.sendRedirect(request.getServletContext().getContextPath() + "/HomePage");
    }

    public void destroy() {
    }
}

interface UsernameGenerator {
    String getUsername(String username, String password)
            throws ClientNotFoundException, NonUniqueClientException;
}