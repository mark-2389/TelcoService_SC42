package it.polimi.db2.telcoservice_sc42.servlets;

import java.io.*;

import it.polimi.db2.telcoservice_sc42.entities.Client;
import it.polimi.db2.telcoservice_sc42.exception.ClientNotFoundException;
import it.polimi.db2.telcoservice_sc42.exception.NonUniqueClientException;
import it.polimi.db2.telcoservice_sc42.services.ClientService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;


@WebServlet(name = "loginServlet", value = "/login")
public class LoginServlet extends HttpServlet {

    @EJB(name = "it.polimi.db2.telcoservice_sc42.services/LoginService")
    private ClientService clientService;

    public LoginServlet() {
        super();
    }

    public void init() throws ServletException {

    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("POST called");
        handleRequest(req, resp);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        handleRequest(request, response);
    }

    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Client client;

        try {
            client = clientService.checkCredentials(username, password);
        } catch (NonUniqueClientException | ClientNotFoundException exception) {
            String caller = getCaller(request);

            if ( caller != null )
                response.sendRedirect(request.getServletContext().getContextPath() + "/" + caller);
            return;
        }
        request.getSession().setAttribute("username", client.getUsername());
        response.sendRedirect(request.getServletContext().getContextPath() + "/HTML/home.jsp");
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

    public void destroy() {
    }
}