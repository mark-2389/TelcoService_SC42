package it.polimi.db2.telcoservice_sc42;

import java.io.*;
import java.sql.DriverManager;

import it.polimi.db2.telcoservice_sc42.entities.Client;
import it.polimi.db2.telcoservice_sc42.services.LoginService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.thymeleaf.TemplateEngine;


@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {

    @EJB(name = "it.polimi.db2.telcoservice_sc42.services/LoginService")
    private LoginService loginService;

    public HelloServlet() {
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

    public void handleRequest(HttpServletRequest req, HttpServletResponse res) throws IOException {

        PrintWriter out = res.getWriter();
        res.setContentType("text/plain");

        String username = req.getParameter("username");
        String password = req.getParameter("password");


        Client client = loginService.checkCredentials(username, password);


        out.write("username");
        out.write(" = ");
        out.write(client.getUsername());
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