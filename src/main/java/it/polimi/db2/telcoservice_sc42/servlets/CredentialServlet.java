package it.polimi.db2.telcoservice_sc42.servlets;

import it.polimi.db2.telcoservice_sc42.exception.NonUniqueClientException;
import it.polimi.db2.telcoservice_sc42.services.ClientService;
import jakarta.ejb.EJB;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "credentialServlet", value = "/register")
public class CredentialServlet extends HttpServlet {
    @EJB(name = "it.polimi.db2.telcoservice_sc42.services/LoginService")
    private ClientService clientService;

    public CredentialServlet() {
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

    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");

        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            clientService.addClient(username, email, password);
        } catch ( NonUniqueClientException e ) {
            System.out.println("Client already in database");

            String caller = getCaller(request);

            if ( caller != null ) {
                response.sendRedirect(getServletContext().getContextPath() + "/" + caller);
            }

            return;
        }

        response.sendRedirect(getServletContext().getContextPath() + "/index.jsp");
    }

    private String getCaller(HttpServletRequest request) {
        if ( request.getParameterValues("landingRegister") != null ) {
            return "index.jsp";
        }

        if ( request.getParameterValues("register") != null ) {
            return "HTML/registration.jsp";
        }

        return null;
    }

    public void destroy() {
    }
}
