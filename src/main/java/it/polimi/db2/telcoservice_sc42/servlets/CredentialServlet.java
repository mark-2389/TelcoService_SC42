package it.polimi.db2.telcoservice_sc42.servlets;

import it.polimi.db2.telcoservice_sc42.entities.Client;
import it.polimi.db2.telcoservice_sc42.exception.NonUniqueClientException;
import it.polimi.db2.telcoservice_sc42.services.ClientService;
import jakarta.ejb.EJB;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "credentialServlet", value = "/register")
public class CredentialServlet extends HttpServlet {
    @EJB(name = "it.polimi.db2.telcoservice_sc42.services/LoginService")
    private ClientService clientService;

    public CredentialServlet() {
        super();
    }

    public void init() throws ServletException {

    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("POST called");
        handleRequest(req, resp);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        handleRequest(request, response);
    }

    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter out = response.getWriter();
        response.setContentType("text/plain");

        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");


        Client client;

        try {
            client = clientService.addClient(username, email, password);
        } catch ( NonUniqueClientException e ) {
            response.sendRedirect(getServletContext().getContextPath() + "/index.jsp");
            return;
        }


        out.write("username");
        out.write(" = ");
        out.write(client.getUsername());
        out.write("\n");
        out.write("email");
        out.write(" = ");
        out.write(client.getEmail());
        out.write("\n");
        out.write("password");
        out.write(" = ");
        out.write(client.getPassword());
        out.write("\n");
        out.close();

    }

    public void destroy() {
    }
}
